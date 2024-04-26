import React, { useState, useEffect } from 'react';
import { Button, Textarea, Td, Tr, Tbody, Table, TableContainer, RangeSlider, RangeSliderTrack, RangeSliderFilledTrack, RangeSliderThumb } from '@chakra-ui/react';
import '../style/UserReservation.css';
import { useAuth } from './LoginForm';
import Notification from './Notification';

function UserReservation({ reservation }) {
  const { authHeader } = useAuth();
  const [newReview, setNewReview] = useState('');
  const [rating, setRating] = useState(0); 

  const cancelReservation = async (reservationId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/reservation/${reservationId}`, {
        method: 'DELETE',
        headers: {
          Authorization: 'Basic ' + localStorage.getItem('authHeader'),
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        alert('Error cancelling reservation:')
        console.error('Error cancelling reservation:', response.statusText)

        return;
      }
      alert('Reservation cancelled successfully')
      console.log('Reservation cancelled successfully');
    } catch (error) {
      console.error('Error deleting reservation:', error);
    }
  };


  
  const handleReviewSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch(`http://localhost:8080/api/reservation/${reservation.id}/review`, {
        method: 'POST',
        headers: {
          Authorization: `Basic ` + localStorage.getItem('authHeader'),
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          text: newReview,
          rating: rating,
        }),
      });

      console.log(newReview, rating);
      if (!response.ok) {
        alert('Failed to submit review')
        throw new Error('Failed to submit review');
      }

    } catch (error) {
      alert('Error submitting review')
      console.error('Error submitting review:', error);
    }
    alert('Success in adding review')
    console.log('Success in adding review')
  };

  return (
    <div className='booking'>
      <h3>Reservation id {reservation.id}</h3>
      <div className='reservation--row'>
        <div className='reservation--image'>
          <img
            src='https://thumbor.forbes.com/thumbor/fit-in/900x510/https://www.forbes.com/home-improvement/wp-content/uploads/2022/07/download-23.jpg'
            alt='Apartment'
          />
        </div>
        <TableContainer>
          <Table variant='simple'>
            <Tbody>
              <Tr>
                <Td>Apartament name</Td>
                <Td>{reservation.apartment.name}</Td>
              </Tr>
              <Tr>
                <Td>Apartament reservation dates</Td>
                <Td>{`${reservation.startDate} - ${reservation.endDate}`}</Td>
              </Tr>
              <Tr>
                <Td>Apartament pricing</Td>
                <Td>{reservation.price}</Td>
              </Tr>
              <Tr>
                <Td>Apartament client</Td>
                <Td>{reservation.client.username}</Td>
              </Tr>
              <Tr>
                <Td>Apartament status</Td>
                <Td>{reservation.currentStatus}</Td>
              </Tr>
            </Tbody>
          </Table>
        </TableContainer>
      </div>
      
      <Button colorScheme='red' onClick={() => cancelReservation(reservation.id)}>Cancel reservation</Button>
      <div className='user--review'>
        <h2>Add review</h2>
        <form onSubmit={handleReviewSubmit}>
          <Textarea
            placeholder='Tell us about your experience...'
            onChange={(e) => setNewReview(e.target.value)}
            value={newReview}
          />
          <RangeSlider defaultValue={[0]} min={0} max={10} step={1} onChangeEnd={(val) => setRating(val[0])}>
            <RangeSliderTrack bg='red.100'>
              <RangeSliderFilledTrack bg='tomato' />
            </RangeSliderTrack>
            <RangeSliderThumb boxSize={10} index={0} />
          </RangeSlider>
          <Button type='submit' style={{ margin: '1vw' }} colorScheme='blue'>
            Add review
          </Button>
        </form>
      </div>
    </div>
  );
}

export default UserReservation;