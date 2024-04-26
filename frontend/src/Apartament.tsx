import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Button, TableContainer, Tbody, Tr, Td, Table, Box, Select } from '@chakra-ui/react';
import Review from './components/Review';
import './style/Apartment.css'
import Notification from './components/Notification';

type AvailablePeriods = {
  identifier: number;
  startDate: string;
  endDate: string;
};

type ReviewType = {
  id: number;
  rating: number;
  text: string;
};

type ApartmentProps = {
  identifier: number;
  name: string;
  location: string;
  image: string;
  periods: AvailablePeriods[];
  pricePerNight: number;
  reviews: ReviewType[];
  photo: string;
};

function Apartament() {
  const { identifier: urlIdentifier } = useParams<{ identifier?: string }>();
  const id = urlIdentifier || '0';
  const idNumber = parseInt(id, 10);
  const [apartmentData, setApartmentData] = useState<ApartmentProps | null>(null);
  const [selectedDates, setSelectedDates] = useState<string[]>([]);
  const [startDate, setStartDate] = useState<string>('');
  const [endDate, setEndDate] = useState<string>('');
  const [noPeople, setNoPeople] = useState(0);
  const [showNotification, setShowNotification] = useState({ text: '', type: 'success' })

  const resetNotification = () => {
    setShowNotification({ text: '', type: 'success' });
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/apartment/${idNumber}`, {
          method: 'GET',
          headers: {
            Authorization: 'Basic ' + localStorage.getItem('authHeader'),
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          console.error('Error fetching apartment data:', response.statusText);
          return;
        }

        const data: ApartmentProps = await response.json();
        setApartmentData(data);
      } catch (error) {
        console.error('Error fetching apartment data:', error);
      }
    };

    fetchData();
  }, [idNumber]);

  if (!apartmentData) {
    return <div>Loading...</div>;
  }

  console.log(apartmentData);
  console.log(localStorage.getItem('role'))

  const handleBookingSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await fetch(`http://localhost:8080/api/apartment/${idNumber}/check?endDate=${endDate}&startDate=${startDate}&persons=${noPeople}`, {
        method: 'GET',
        headers: {
          Authorization: 'Basic ' + localStorage.getItem('authHeader'),
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        console.error('Error checking availability:', response.statusText);
        return;
      }

      console.log('Apartment is available for booking');
    } catch (error) {
      console.error('Error checking availability:', error);
    }

    try {
      const response = await fetch('http://localhost:8080/api/reservation', {
        method: 'POST',
        headers: {
          Authorization: 'Basic ' + localStorage.getItem('authHeader'),
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          apartmentId: apartmentData.id,
          startDate: startDate,
          endDate: endDate,
        }),
      });

      if (!response.ok) {
        alert('Chosen dates are not available')
        console.error('Error creating reservation:', response.statusText);
        return;
      }

      alert('Reservation created successfully')
      console.log('Reservation created successfully');
    } catch (error) {
      alert('Chosen dates are not available');
      console.error('Error creating reservation:', error);
    }
  };

  const markedDates = apartmentData.periods.map((period) => ({
    startDate: new Date(period.startDate),
    endDate: new Date(period.endDate),
  }));

  console.log(showNotification)
  return (
    <>
      <div className='single--apartament'>
        <div className='apartment--image'>
          <img src={apartmentData.photo} alt={apartmentData.name} />
        </div>
        <div className='apartment--booking' >
          <TableContainer>
            <Table variant='simple'>
              <Tbody>
                <Tr>
                  <Td><b>Apartament name</b></Td>
                  <Td>{apartmentData.name}</Td>
                </Tr>
                <Tr>
                  <Td><b>Apartament location</b></Td>
                  <Td>{apartmentData.location}</Td>
                </Tr>
                <Tr>
                  <Td><b>Price per night</b></Td>
                  <Td>{apartmentData.pricePerNight} $</Td>
                </Tr>
              </Tbody>
            </Table>
          </TableContainer>
          {localStorage.getItem('role') === 'CLIENT' && (<div className='booking' >
          <form onSubmit={handleBookingSubmit}>
            <h2><b>Available periods:</b></h2>
            <div className='booking--date'>
  <Box mt={4}>
    <ul>
      {markedDates.map((dateRange, index) => (
        <li key={index}>
          {`${dateRange.startDate.toDateString()} to ${dateRange.endDate.toDateString()}`}
        </li>
      ))}
    </ul>
    <label>
      <b>Start date:</b>
      <input type='date' value={startDate} onChange={(e) => setStartDate(e.target.value)} required />
    </label>
    <label>
      <b>End date:</b>
      <input type='date' value={endDate} onChange={(e) => setEndDate(e.target.value)} required />
    </label>
  </Box>
</div>
<label>
<b>Number of people</b>
<input
        type='number'
        value={noPeople}
        onChange={(e) => setNoPeople(parseInt(e.target.value, 10))}
        required
      />
    </label>
            <Button colorScheme='blue' type='submit'>Book</Button>
          </form>
        </div>)}
      </div>
      </div>
      <div className='reviews'>
        <h2>Reviews</h2>
        <div className='reviews--container'>
          {apartmentData.reviews.map((review) => (
            <Review key={review.id} id={review.id} rating={review.rating} text={review.text} />
          ))}
        </div>
        <div className='notification'>
        {showNotification.text && (
          <Notification text={showNotification.text} type={showNotification.type} onReset={resetNotification}/>
        )}
      </div>
      </div>
    </>
  );
}

export default Apartament;