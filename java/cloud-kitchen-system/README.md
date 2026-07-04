# Cloud-Kitchen System

A Java Swing-based Cloud-Kitchen management system that handles user registration, restaurant management, menu items, and order processing.

## Features

- User Management
  - User registration and login
  - Profile management with contact information
- Restaurant Management
  - Restaurant registration
  - Menu item management
  - Price and description updates
- Order Processing
  - Place orders from restaurant menus
  - Track order status
  - View order history
- Delivery Management
  - Assign delivery personnel
  - Track delivery status
  - Update delivery locations

## Project Structure

```
src/
├── gui/           # Swing UI components
├── main/          # Application entry point
├── model/         # Data models
├── service/       # Business logic
└── util/          # Utilities (file handling, etc.)
```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java Runtime Environment (JRE)
- Windows OS (for using the provided batch files)

## Quick Start

1. Clone or download this repository
2. Navigate to the project root directory
3. Run the application:
   ```batch
   run.bat
   ```

This will:
- Create the `out` directory if it doesn't exist
- Compile all Java source files
- Launch the application

## Development

### Project Components

- **Models** (`src/model/`)
  - `User.java`: User account information
  - `Restaurant.java`: Restaurant details and menu
  - `MenuItem.java`: Food items with prices
  - `Order.java`: Order tracking and status
  - `DeliveryPerson.java`: Delivery personnel management

- **Services** (`src/service/`)
  - `UserService.java`: User authentication and registration
  - `RestaurantService.java`: Restaurant and menu management
  - `OrderService.java`: Order processing
  - `DeliveryService.java`: Delivery assignment and tracking

- **GUI** (`src/gui/`)
  - `MainFrame.java`: Main application window
  - `UserLoginPanel.java`: Login interface
  - `UserRegistrationPanel.java`: Registration form
  - `RestaurantMenuPanel.java`: Menu management
  - `OrderPanel.java`: Order placement interface

### Data Storage

The system uses Java serialization to persist data:
- User accounts: `users.ser`
- Restaurants: `restaurants.ser`
- Orders: `orders.ser`
- Delivery personnel: `delivery_people.ser`
- Menu items: `menu_items.ser`

## Building from Source

1. Ensure JDK is installed and JAVA_HOME is set
2. Open a terminal in the project root
3. Run the build script:
   ```batch
   run.bat
   ```

## Usage Guide

1. **First-Time Setup**
   - Launch the application
   - Register a new user account
   - (For restaurants) Register your restaurant

2. **For Customers**
   - Log in with your credentials
   - Browse restaurant menus
   - Place orders
   - Track delivery status

3. **For Restaurants**
   - Log in to restaurant portal
   - Manage menu items
   - Update prices
   - View incoming orders

4. **For Delivery Personnel**
   - Log in to delivery portal
   - View assigned deliveries
   - Update delivery status
   - Mark orders as delivered

## Troubleshooting

- If compilation fails, ensure JDK is properly installed and JAVA_HOME is set
- If data files are missing, the system will create new ones automatically
- For spaces in file paths, the build script handles them automatically

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is open-source and available under the MIT License.
