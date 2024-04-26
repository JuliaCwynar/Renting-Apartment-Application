import React, { useState, useEffect } from 'react';
import ManagerReservation from './components/ManagerReservation';
import UserReservation from './components/UserReservation';
import OwnerReservation from './components/OwnerReservation';

function Profile() {
  const [userRole, setUserRole] = useState(localStorage.getItem('role'));
  const [reservations, setReservations] = useState([]);
  console.log('role');
  const fetchData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/reservation/list`, {
        method: 'GET',
        headers: {
          Authorization: 'Basic ' + localStorage.getItem('authHeader'),
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        console.error('Error fetching reservation data:', response.statusText);
        return;
      }

      const data = await response.json();
      setReservations(data);
      console.log('Fetched reservations:', data);
    } catch (error) {
      console.error('Error fetching reservation data:', error);
    }
  };

  useEffect(() => {
    fetchData();

    const intervalId = setInterval(() => {
      fetchData();
    }, 5000);
    return () => clearInterval(intervalId);
  }, []); 
 

  console.log(reservations.length)

  const renderBookingsComponent = () => {
    if (userRole === 'MANAGER') {
      return reservations.length > 0
        ? reservations.map((reservation) => <ManagerReservation key={reservation.id} reservation={reservation} />)
        : 'No reservations';
    } else if (userRole === 'CLIENT') {
      return reservations.length > 0
        ? reservations.map((reservation) => <UserReservation key={reservation.id} reservation={reservation} />)
        : 'No reservations';
    } else if (userRole === 'OWNER') {
      return <OwnerReservation />;
    }
  };

  console.log(userRole);

  return (
    <div className='bookings-container'>
      <h2 style={{fontSize: '2vw'}}>You are logged as <b>{userRole}</b></h2>
      {userRole ? renderBookingsComponent() : <p>Please log in to view bookings.</p>}
    </div>
  );
}

export default Profile;