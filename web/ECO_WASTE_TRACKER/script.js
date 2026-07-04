// Initialize user data and waste log
let currentUser = localStorage.getItem('currentUser');
let users = JSON.parse(localStorage.getItem('users')) || {};
let wasteLog = users[currentUser] ? users[currentUser].wasteLog : [];

// Add event listener for DOM content loaded
document.addEventListener('DOMContentLoaded', () => {
    // User Authentication
    const registerForm = document.getElementById('registerForm');
    const loginForm = document.getElementById('loginForm');
    if (registerForm) {
        registerForm.addEventListener('submit', registerUser);
    }
    if (loginForm) {
        loginForm.addEventListener('submit', loginUser);
    }

    // Waste Logging and Dashboard
    const wasteForm = document.getElementById('wasteForm');
    if (wasteForm) {
        wasteForm.addEventListener('submit', logWaste);
    }

    const wasteBarChartCanvas = document.getElementById('wasteBarChart');
    const wastePieChartCanvas = document.getElementById('wastePieChart');
    if (wasteBarChartCanvas && wastePieChartCanvas) {
        loadWasteData();
        displayWasteCharts();
    }

    // Reports Page
    const reportsSection = document.getElementById('reports');
    if (reportsSection) {
        generateReports();
    }
});

// User Registration
function registerUser(event) {
    event.preventDefault();

    const username = document.getElementById('regUsername').value;
    const password = document.getElementById('regPassword').value;

    if (username && password && !users[username]) {
        users[username] = { password: password, wasteLog: [] };
        localStorage.setItem('users', JSON.stringify(users));
        alert('Registration successful!');
        window.location.href = 'login.html';
    } else {
        alert('Invalid username or user already exists.');
    }
}

// User Login
function loginUser(event) {
    event.preventDefault();

    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    if (users[username] && users[username].password === password) {
        currentUser = username;
        wasteLog = users[username].wasteLog;
        localStorage.setItem('currentUser', currentUser);
        alert('Login successful!');
        window.location.href = 'log.html';
    } else {
        alert('Invalid username or password.');
    }
}

// Function to log waste
function logWaste(event) {
    event.preventDefault();

    const wasteType = document.getElementById('wasteType').value;
    const quantity = parseFloat(document.getElementById('quantity').value);

    if (!isNaN(quantity) && quantity > 0) {
        const wasteEntry = {
            type: wasteType,
            quantity: quantity,
            date: new Date()
        };

        wasteLog.push(wasteEntry);
        users[currentUser].wasteLog = wasteLog;
        saveUserData();
        alert('Waste logged successfully!');
        event.target.reset();
    } else {
        alert('Please enter a valid quantity.');
    }
}

// Function to save user data to local storage
function saveUserData() {
    localStorage.setItem('users', JSON.stringify(users));
}

// Function to load waste data
function loadWasteData() {
    if (currentUser && users[currentUser]) {
        wasteLog = users[currentUser].wasteLog;
    }
}

// Function to display waste data in charts
function displayWasteCharts() {
    const ctxBar = document.getElementById('wasteBarChart').getContext('2d');
    const ctxPie = document.getElementById('wastePieChart').getContext('2d');

    // Aggregate waste data by type
    const wasteData = wasteLog.reduce((acc, entry) => {
        if (acc[entry.type]) {
            acc[entry.type] += entry.quantity;
        } else {
            acc[entry.type] = entry.quantity;
        }
        return acc;
    }, {});

    // Prepare data for the charts
    const labels = Object.keys(wasteData);
    const data = Object.values(wasteData);

    // Create a bar chart using Chart.js
    new Chart(ctxBar, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Waste Quantity (kg)',
                data: data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Create a pie chart using Chart.js
    new Chart(ctxPie, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: 'Waste Distribution',
                data: data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1
            }]
        }
    });
}

// Function to generate reports
function generateReports() {
    const reportTypes = ['Daily', 'Weekly', 'Monthly'];
    reportTypes.forEach(type => {
        const reportData = aggregateWasteData(type.toLowerCase());
        displayReport(type, reportData);
    });
}

// Function to aggregate waste data for reports
function aggregateWasteData(period) {
    const now = new Date();
    const periodStart = new Date(now);
    if (period === 'daily') {
        periodStart.setDate(now.getDate() - 1);
    } else if (period === 'weekly') {
        periodStart.setDate(now.getDate() - 7);
    } else if (period === 'monthly') {
        periodStart.setMonth(now.getMonth() - 1);
    }

    return wasteLog.reduce((acc, entry) => {
        const entryDate = new Date(entry.date);
        if (entryDate >= periodStart && entryDate <= now) {
            if (acc[entry.type]) {
                acc[entry.type] += entry.quantity;
            } else {
                acc[entry.type] = entry.quantity;
            }
        }
        return acc;
    }, {});
}

// Function to display report data
function displayReport(type, data) {
    const reportSection = document.getElementById('reports');
    const reportDiv = document.createElement('div');
    reportDiv.classList.add('report');
    const reportTitle = document.createElement('h3');
    reportTitle.textContent = `${type} Report`;
    reportDiv.appendChild(reportTitle);

    const reportList = document.createElement('ul');
    Object.keys(data).forEach(wasteType => {
        const listItem = document.createElement('li');
        listItem.textContent = `${wasteType}: ${data[wasteType]} kg`;
        reportList.appendChild(listItem);
    });

    reportDiv.appendChild(reportList);
    reportSection.appendChild(reportDiv);
}


