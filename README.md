# Blood-Bank-Management-System
A mobile application for managing blood donations, requests, and inventory in a blood bank. The application allows users to register, view blood stock, request specific blood types, donate blood, and locate nearby blood bank branches.

Features
User Registration and Login: Users can register with their name, age, blood group, contact, and password. This allows personalized access to services like requesting blood and viewing the status of their requests.

Blood Stock Management: The system maintains an inventory of blood stocks, categorized by blood type and quantity. The stock updates dynamically based on blood donations and requests.

Blood Donation: Donors can register their donations, which updates the blood stock in real-time. Donation records include donor name, contact details, blood type, amount, and optional notes.

Blood Request: Users can request specific blood types, with the system automatically deducting the requested amount from the inventory if stock is available. If stock is insufficient, the request will be declined.

Nearby Blood Bank Branches: Using stored branch data, users can locate nearby blood bank branches with details on location coordinates.

Technical Details
Database Management: The app uses SQLite for offline data storage, managing multiple tables including users, blood stock, donations, requests, and branch locations.

Stock Update and Validation: Real-time validation and updating of blood stock levels ensure that inventory reflects actual availability.

RecyclerView for Displaying Data: Blood stock information is displayed in a RecyclerView for a clean, scrollable view.

Google Maps Integration (optional): Integration-ready for Google Maps to display branch locations based on GPS coordinates.
