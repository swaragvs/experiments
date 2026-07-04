// Add this to your project to test the TCP connection separately
package com.swarag.laplock

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException

/**
 * Utility class to test TCP connections
 */
class ConnectionTester {
    
    companion object {
        private const val TAG = "ConnectionTester"
        
        /**
         * Test if we can reach the Flask API server
         */
        fun testApiServer(context: Context, apiServer: String) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val url = "http://$apiServer/ping"
                    Log.d(TAG, "Testing connection to API server: $url")
                    
                    val conn = java.net.URL(url).openConnection() as java.net.HttpURLConnection
                    conn.connectTimeout = 5000
                    conn.readTimeout = 5000
                    conn.requestMethod = "GET"
                    
                    val responseCode = conn.responseCode
                    
                    GlobalScope.launch(Dispatchers.Main) {
                        if (responseCode == 200) {
                            Toast.makeText(context, "API Server connection successful!", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "API Server connection successful!")
                        } else {
                            Toast.makeText(context, "API Server returned code: $responseCode", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "API Server returned code: $responseCode")
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "API Server connection failed: ${e.message}")
                    e.printStackTrace()
                    
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(context, "API Server connection failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        
        /**
         * Test if we can reach the TCP command server
         */
        fun testTcpServer(context: Context, serverIp: String, port: Int) {
            GlobalScope.launch(Dispatchers.IO) {
                var socket: Socket? = null
                
                try {
                    Log.d(TAG, "Testing TCP connection to $serverIp:$port")
                    
                    // Create socket with timeout
                    socket = Socket()
                    socket.connect(InetSocketAddress(serverIp, port), 5000)
                    
                    val writer = PrintWriter(socket.getOutputStream(), true)
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    
                    // Send a TEST command
                    Log.d(TAG, "Sending TEST command")
                    writer.println("TEST")
                    writer.flush()
                    
                    // Try to read response with timeout
                    socket.soTimeout = 3000
                    
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(context, "TCP Server connection successful!", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "TCP Server connection successful!")
                    }
                    
                } catch (e: SocketTimeoutException) {
                    Log.e(TAG, "TCP connection timed out: ${e.message}")
                    
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(context, "TCP connection timed out", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "TCP connection failed: ${e.message}")
                    e.printStackTrace()
                    
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(context, "TCP connection failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                } finally {
                    try {
                        socket?.close()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error closing socket: ${e.message}")
                    }
                }
            }
        }
    }
}