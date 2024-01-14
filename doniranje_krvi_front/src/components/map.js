import React, { useState } from 'react';
import { APIProvider, Map, Marker, InfoWindow } from '@vis.gl/react-google-maps';
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const YourComponent = () => {
  const position = { lat: 45.3272, lng: 14.4411 };

  const [open, setOpen] = useState(false);
  const [infoWindowPosition, setInfoWindowPosition] = useState(null);
  const [clickedMarkerName, setClickedMarkerName] = useState(null);
  const [locations,setLocations] = useState([]);

  const handleMarkerClick = (markerPosition, markerName) => {
    setInfoWindowPosition(markerPosition);
    setClickedMarkerName(markerName);
    setOpen(true);
  };

  
  const pretrazivanje = async() =>{
    const token = localStorage.getItem("token");
    const decodedToken = jwtDecode(token);

      try{
        const response = await axios.get(`${baseURL}/user/getLocations/${decodedToken.sub}`,{
          headers: {
              Authorization: `Bearer ${token}`,
            },
        });
        setLocations(response.data);
        console.log(response.data);
      }catch(error){

      }
  };


  return (

    <div id="map" className="body-map">
      <APIProvider apiKey="AIzaSyDJlArc7yrXyc4EoDU2Yaq9wJ7EttzrjJg">
        <div className="map_div">
          <Map zoom={9} center={position}>
            <Marker
              position={position}
              onClick={() => handleMarkerClick(position, 'KBC Rijeka')}
            ></Marker>
            <Marker
              position={{ lat: 43.5100, lng: 16.4400 }}
              onClick={() => handleMarkerClick({ lat: 43.5100, lng: 16.4400 }, 'KBC Split')}
            ></Marker>
            <Marker
              position={{ lat: 45.8167, lng: 15.9833 }}
              onClick={() => handleMarkerClick({ lat: 45.8167, lng: 15.9833 }, 'Zagreb')}
            ></Marker>

            {open && (
              <InfoWindow
                position={infoWindowPosition}
                onCloseClick={() => setOpen(false)}
              >
                <p>{clickedMarkerName}</p>
              </InfoWindow>
            )}
          </Map>
        </div>
      </APIProvider>

      <div id="popis_mjesta">
        {
        (localStorage.token) ? (
          <a>
            <button className="map-btn" onClick={pretrazivanje}>Pretraži</button>
            </a>
        ) : (
          <div className="dark-background">
            <a href="./prijava">
            <button className="map-btn">Prijavi se <br></br>za odabir termina</button>
            </a>
            <>{clickedMarkerName ? `${clickedMarkerName}` : 'Popis mjesta'}</>
          </div>
        )
        }
      </div>

    </div>
  );
};

export default YourComponent;
