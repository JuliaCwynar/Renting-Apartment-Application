# Apartment Rental Application

This is a web application designed to facilitate the renting and management of apartments. The application caters to clients, managers, and apartment owners.

## Table of Contents


- [Features](#features)
  - [For Clients](#for-clients)
  - [For Managers](#for-managers)
  - [For Apartment Owners](#for-apartment-owners)
- [Covered Topics](#covered-topics)
- [Technologies Used](#technologies-used)
  - [Backend](#backend)
  - [Database](#database)
  - [Frontend](#frontend)
- [Getting Started](#getting-started)
  - [Run Server Application](#run-server-application)
  - [Run Client Application](#run-client-application)

## Features

### For Clients
- **Log in/Log out**: Secure authentication for accessing the application.
- **List Apartments**: View available apartments for rent.
- **Book Apartments**: Reserve apartments for specified dates.
- **Check Availability**: Verify if an apartment is available for desired dates.
- **Add Reviews**: Submit reviews for rented apartments.
- **Show Apartment Details**: View detailed information about each apartment.
- **Show Reservation Details**: View details of current and past reservations.
- **List Booked Apartments**: See a list of apartments you have booked.
- **Delete Reservation**: Cancel a reservation.

### For Managers
- **Change Reservation Status**: Update the current status of a reservation.
- **Add New Apartments**: Add new apartments to the listing.
- **Add Available Periods**: Specify availability periods for apartments.
- **Delete Available Periods**: Remove availability periods for apartments.

### For Apartment Owners
- **Manage Listings**: Update apartment details and manage availability.
- **Monitor Bookings**: Keep track of apartment bookings and reservations.

## Covered Topics

- **REST API**: Robust backend API to handle all application requests.
- **Security**: Authentication and access control to ensure secure operations.
- **IFML Model**: Interaction Flow Modeling Language for user interaction modeling.
- **State Management**: Efficient state management for a smooth user experience.
- **User Stories**: Detailed user stories to guide development and usage.
- **Interface Evaluation and Design**: User-friendly interface design based on evaluation and feedback.

## Technologies Used

### Backend
- **Kotlin**
- **Spring Boot**

### Database
- **PostgreSQL**

### Frontend
- **Node.js**
- **TypeScript**
- **React.js**


## Getting Started

### Run Server Application

1. **Connect Database**: Ensure PostgreSQL is set up and running.
2. **Start Backend**: Follow the instructions in the backend directory to run the Spring Boot application.

### Run Client Application

1. **Install Dependencies**: Navigate to the client directory and run:
    ```bash
    npm install
    ```
2. **Run Application**: Start the development server with:
    ```bash
    npm run dev
    ```

