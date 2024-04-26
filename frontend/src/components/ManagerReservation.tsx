import React, { useState } from 'react';
import { Td, Tr, Tbody, Table, TableContainer, Button } from '@chakra-ui/react';
import '../style/ManagerReservation.css';
import { useAuth } from './LoginForm';

function ManagerReservation({ reservation }) {
  const { authHeader } = useAuth();

  const updateReservationStatus = async (reservationId: number, newStatus: string) => {
  try {
    const url = `http://localhost:8080/api/reservation/${reservationId}/change-status?newStatus=${newStatus}`;

    const response = await fetch(url, {
      method: 'PUT',
      headers: {
        Authorization: 'Basic ' + localStorage.getItem('authHeader'),
        'Content-Type': 'application/json',
      },
     
    });

    if (!response.ok) {
      console.error('Error updating reservation:', response.statusText);
      
      return;
    }

    
    console.log('Reservation status updated successfully');
    alert('Reservation status updated successfully')
  } catch (error) {
    console.error('Error updating reservation:', error);
  }
};


  const deleteReservation = async (reservationId: number) => {
    try {
      const response = await fetch(`http://localhost:8080/api/reservation/${reservationId}`, {
        method: 'DELETE',
        headers: {
          Authorization: 'Basic ' + localStorage.getItem('authHeader'),
          'Content-Type': 'application/json', 
        },
      });

      if (!response.ok) {
        console.error('Error deleting reservation:', response.statusText);
        alert('Error deleting reservation')
        
        return;
      }

      console.log('Reservation deleted successfully');
      alert('Reservation deleted successfully')
    } catch (error) {
      console.error('Error deleting reservation:', error);
      alert('Error deleting reservation')
    }
  };

  return (
    <div className='booking'>
      <div className='reservation--row'>
        <div className='apartment--image'>
          <img
            src='https://thumbor.forbes.com/thumbor/fit-in/900x510/https://www.forbes.com/home-improvement/wp-content/uploads/2022/07/download-23.jpg'
            alt='Apartment'
          />
        </div>
        <TableContainer style={{width: '100%'}}>
          <h2><b>Reservation nr {reservation.id}</b></h2>
          <Table variant='simple'>
            <Tbody>
              <Tr>
                <Td>Apartament name</Td>
                <Td><b>{reservation.apartment.name}</b></Td>
              </Tr>
              <Tr>
                <Td>Apartament reservation dates</Td>
                <Td><b>{`${reservation.startDate} - ${reservation.endDate}`}</b></Td>
              </Tr>
              <Tr>
                <Td>Apartament pricing</Td>
                <Td><b>{reservation.price}</b></Td>
              </Tr>
              <Tr>
                <Td>Apartament client</Td>
                <Td><b>{reservation.client.username}</b></Td>
              </Tr>
              <Tr>
                <Td>Apartament status</Td>
                <Td><b>{reservation.currentStatus}</b></Td>
              </Tr>
            </Tbody>
          </Table>
        </TableContainer>
      </div>
      <div className='manage--booking'>
        <h2><b>Manage booking</b></h2>
        <form>
          <select name='status' id='status' onChange={(e) => updateReservationStatus(reservation.id, e.target.value)}>
            <option value="AVAILABLE">Available</option>
            <option value="UNDER_CONSIDERATION">Under consideration</option>
            <option value="BOOKED">Booked</option>
            <option value="OCCUPIED">Occupied</option>
            <option value="AWAITING_REVIEW">Awaiting review</option>
            <option value="CLOSED">Closed</option>
          </select>
          <Button colorScheme='blue' type='button' onClick={() => updateReservationStatus(reservation.id, document.getElementById('status').value)}>Update booking</Button>
        </form>
        <Button colorScheme='red' onClick={() => deleteReservation(reservation.id)}>Delete reservation</Button>
      </div>
    </div>
  );
}

export default ManagerReservation;