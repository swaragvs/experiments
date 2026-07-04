package com.swarag.laplock

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.swarag.laplock.ui.theme.LaplockTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    // Use mutableStateOf for UI state that needs to be updated
    private var serverIp by mutableStateOf("Loading...")
    private val SERVER_PORT = 8888 // TCP port to send commands

    // The special 10.0.2.2 IP address allows the Android emulator
    // to connect to the host machine's localhost (your laptop)
    // Change this to your actual laptop's IP address when testing on a real device
    private val API_SERVER = "192.168.56.1:9999" // API server endpoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Create preference manager
        val prefManager = PreferenceManager(this)

        // Check if we have a saved IP first
        val savedIp = prefManager.getSavedServerIp()
        if (savedIp != null && savedIp.isNotEmpty()) {
            Log.d("MainActivity", "Using saved IP: $savedIp")
            serverIp = savedIp
        } else {
            // If no saved IP, fetch it from the server
            fetchLaptopIp()
        }

        setContent {
            LaplockTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        onLockClick = { sendLockCommand() },
                        onRefreshIpClick = { fetchLaptopIp() },
                        serverIp = serverIp,
                        apiServer = API_SERVER,
                        onManualIpChanged = { newIp ->
                            // Update the server IP when manually changed
                            serverIp = newIp
                            Log.d("MainActivity", "IP manually changed to: $newIp")
                        }
                    )
                }
            }
        }
    }

    // Fetch the laptop IP dynamically from the Flask API endpoint
    private fun fetchLaptopIp() {
        val url = "http://$API_SERVER/get_ip"
        Log.d("Laptop IP", "Fetching IP from: $url")
        serverIp = "Loading..."

        // Configure OkHttpClient with reasonable timeouts
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.e("Laptop IP", "Failed to fetch IP: ${e.message}")

                // Update UI on main thread
                runOnUiThread {
                    serverIp = "Connection failed"
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to connect: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        Log.e("Laptop IP", "Server error: ${response.code}")
                        runOnUiThread {
                            serverIp = "Error: ${response.code}"
                            Toast.makeText(
                                this@MainActivity,
                                "Server error: ${response.code}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return
                    }

                    val responseBody = response.body?.string()
                    Log.d("Laptop IP", "Response: $responseBody")

                    try {
                        // Properly parse the JSON response
                        val jsonObject = JSONObject(responseBody)
                        val ip = jsonObject.getString("ip")

                        // Update UI on main thread
                        runOnUiThread {
                            serverIp = ip
                            Log.d("Laptop IP", "Updated server IP to: $ip")
                            Toast.makeText(
                                this@MainActivity,
                                "Connected to server at $ip",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Log.e("Laptop IP", "Error parsing response: ${e.message}")
                        runOnUiThread {
                            serverIp = "Parse error"
                            Toast.makeText(
                                this@MainActivity,
                                "Error parsing server response: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })
    }

    // Send a LOCK command to the laptop via TCP
    private fun sendLockCommand() {
        if (serverIp == "Loading..." || serverIp == "Connection failed" || serverIp == "Parse error") {
            Toast.makeText(this, "No valid server IP available", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Attempting to lock laptop at $serverIp:$SERVER_PORT...", Toast.LENGTH_SHORT).show()
        Log.d("TCP Command", "Starting lock attempt to $serverIp:$SERVER_PORT")

        GlobalScope.launch(Dispatchers.IO) {
            var socket: Socket? = null
            var writer: PrintWriter? = null

            try {
                Log.d("TCP Command", "Creating socket to $serverIp:$SERVER_PORT")
                socket = Socket(serverIp, SERVER_PORT)
                writer = PrintWriter(socket.getOutputStream(), true)

                Log.d("TCP Command", "Sending LOCK command")
                writer.println("LOCK") // Send the "LOCK" command to the laptop
                writer.flush()

                // Wait a moment to ensure the command is sent
                Thread.sleep(500)

                Log.d("TCP Command", "Lock command sent successfully")

                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Lock command sent successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("TCP Command", "Error sending LOCK command: ${e.message}")

                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to send lock command: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } finally {
                try {
                    writer?.close()
                    socket?.close()
                    Log.d("TCP Command", "Socket closed")
                } catch (e: Exception) {
                    Log.e("TCP Command", "Error closing resources: ${e.message}")
                }
            }
        }
    }
}