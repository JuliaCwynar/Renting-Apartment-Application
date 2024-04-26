import React, { useState, useEffect } from 'react';
import './style/App.css';
import ApartamentTile from './components/ApartamentTile';
import { Link } from 'react-router-dom';
import { Button } from '@chakra-ui/react';

type ApartmentSimple = {
  id: number;
  name: string;
  location: string;
  photo: string;
};

function App() {
  const [apartmentSimpleData, setApartmentSimpleData] = useState<ApartmentSimple[]>([]);
  const [currentPage, setCurrentPage] = useState<number>(1);
  const apartmentsPerPage = 6;

  useEffect(() => {
    const base64Credentials = localStorage.getItem("authHeader");

    fetch('http://localhost:8080/api/apartment', {
      headers: {
        Authorization: 'Basic ' + base64Credentials,
        'Content-Type': 'application/json',
      },
    })
      .then((res) => res.json())
      .then((data) => {
        setApartmentSimpleData(data);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []);

  console.log(apartmentSimpleData);
  localStorage.getItem('role')

  const indexOfLastApartment = currentPage * apartmentsPerPage;
  const indexOfFirstApartment = indexOfLastApartment - apartmentsPerPage;
  const currentApartments = apartmentSimpleData.slice(indexOfFirstApartment, indexOfLastApartment);

  return (
    <>
      <div className='offer'>
        {currentApartments.map((apartment) => (
          <Link key={apartment.id} to={`/apartment/${apartment.id}`}>
            <ApartamentTile
              photo={apartment.photo}
              id={apartment.id}
              key={apartment.id}
              name={apartment.name}
              price={apartment.pricePerNight}
            />
          </Link>
        ))}
      </div>
      <div>
        <Button  onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 1}>
          Previous
        </Button>
        <span> Page {currentPage} </span>
        <Button  onClick={() => setCurrentPage(currentPage + 1)} disabled={indexOfLastApartment >= apartmentSimpleData.length}>
          Next
        </Button>
      </div>
    </>
  );
}

export default App;