import React, { useState, useEffect } from 'react';
import { Table, Tbody, Tr, Td, TableContainer, Button } from '@chakra-ui/react';
import { useAuth } from './LoginForm';

function OwnerReservation() {
  const { authHeader } = useAuth();
  const [apartmentData, setApartmentData] = useState([]);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  useEffect(() => {
    const base64Credentials = localStorage.getItem('authHeader');

    fetch(`http://localhost:8080/api/apartment`, {
      headers: {
        Authorization: 'Basic ' + base64Credentials,
        'Content-Type': 'application/json',
      },
    })
      .then((res) => res.json())
      .then((data) => {
        setApartmentData(data);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []);

  const addPeriod = async (number) => {
    try {
      const response = await fetch(`http://localhost:8080/api/apartment/${number}/period?startDate=${startDate}&endDate=${endDate}`, {
        method: 'POST',
        headers: {
          Authorization: `Basic ` + localStorage.getItem('authHeader'),
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
        }),
      });

      if (!response.ok) {
        alert('Error adding period')
        throw new Error('Failed to add period');
      
      }

     

      console.log('Period added successfully');
      alert('Period added successfully')
    } catch (error) {
      alert('Error adding period')
      console.error('Error adding period:', error);
    }
  };

  const filtered = apartmentData.filter((x) => x.owner.id == localStorage.getItem('id'));

  if (filtered.length === 0) {
    return <div><h2>You don't own any apartments</h2></div>;
  }

  return (
    <div className='booking'>
      {filtered.map((apartment) => (
        <div className='reservation--row' key={apartment.id}style={{marginBottom: '10vw'}} >
          <div className='apartment--image'>
            <img
              src={apartment.photo}
              alt='Apartment'
            />
          </div>
          <div className='apartment--details' style={{width: '100%'}}>
            <h2>Apartment nr <b>{apartment.id}</b></h2>
            <h2> <b>{apartment.name}</b></h2>
            <h2 style={{padding: '2vw'}}>Available periods</h2>
            <TableContainer  >
              <Table variant='outline'>
                <Tbody>
                  {apartment.periods.map((period) => (
                    <Tr key={period.id}>
                      <Td>{period.startDate}</Td>
                      <Td>to</Td>
                      <Td>{period.endDate}</Td>
                    </Tr>
                  ))}
                </Tbody>
              </Table>
            </TableContainer>
            <div className='date-inputs'>
              <label>Start date:
                <input type='date' value={startDate} onChange={(e) => setStartDate(e.target.value)} />
              </label>
              <label>End date:
                <input type='date' value={endDate} onChange={(e) => setEndDate(e.target.value)} />
              </label>
            </div>
            <Button colorScheme='blue' onClick={() => addPeriod(apartment.id)}>Add period</Button>
          </div>
        </div>
      ))}
    </div>
  );
}

export default OwnerReservation;