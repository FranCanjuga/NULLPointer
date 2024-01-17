import React, { useState } from 'react';
import { APIProvider, Map, Marker, InfoWindow } from '@vis.gl/react-google-maps';
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

function formatDate(dateTime) {
  const date = new Date(dateTime);
  const formattedDate = date.toLocaleDateString();
  return `${formattedDate}`;
}
function formatTime(dateTime) {
  const date = new Date(dateTime);
  const formattedTime = date.toLocaleTimeString();
  return `${formattedTime}`;
}


const YourComponent = () => {
  const position = { lat: 45.3272, lng: 14.4411 };

  const [open, setOpen] = useState(false);
  const [infoWindowPosition, setInfoWindowPosition] = useState(null);
  const [clickedMarkerName, setClickedMarkerName] = useState(null);
  const [locations,setLocations] = useState([]);
  var [pretrazeno,setPret] = useState(false);
  const [appointments,setAppointments] = useState([]);

  const handleMarkerClick = (markerPosition, markerName) => {
    setInfoWindowPosition(markerPosition);
    setClickedMarkerName(markerName);
    setOpen(true);
  };

 const pretrazivanje = async () => {
  const token = localStorage.getItem("token");
  setPret(true);
  console.log(pretrazeno);
  

  try {
    const response = await axios.get(`${baseURL}/user/ActiveAppointments`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log(response.data)
    /*
    console.log(response.data)
    // Izdvoji location_id iz svih appointmenta
    const locationIds = response.data.map(appointment => appointment.location.location_id);
    console.log(locationIds)
    const uniqueLocationIds = Array.from(new Set(locationIds));
    console.log(uniqueLocationIds)

    const locations = response.data.map(appointment => appointment.location);
    console.log("Bato"+locations);

    const uniqueLocations = locations.filter(appointment => uniqueLocationIds.includes(appointment.location.location_id));
    
    setLocations(uniqueLocations);

    console.log(uniqueLocations);
    */
    const locations = response.data.map(appointment => appointment.location);
    setAppointments(response.data)
    setLocations(locations);
    console.log(locations)

  } catch (error) {
    // Obrada greške
  }
};



  return (

    <div id="map" className="body-map">
      <APIProvider apiKey="AIzaSyDJlArc7yrXyc4EoDU2Yaq9wJ7EttzrjJg">
        <div className="map_div">
          <Map zoom={9} center={position}>
            {locations.map((location, index) => (
              <Marker
                key={index}
                position={{ lat: location.latitude, lng: location.longitude }}
                onClick={() => handleMarkerClick(
                  { lat: location.latitude, lng: location.longitude },
                  location.locationName
                )}
              ></Marker>
            ))}
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
              onClick={() => handleMarkerClick({ lat: 45.8167, lng: 15.9833 }, 'Hrvatski zavod za transfuzijsku medicinu')}
            ></Marker>
            <Marker
              position={{ lat: 45.5556, lng: 18.6944 }}
              onClick={() => handleMarkerClick({ lat: 45.5556, lng: 18.6944 }, 'KBC Osijek')}
            ></Marker>
            <Marker
              position={{ lat: 42.6403, lng: 18.1083 }}
              onClick={() => handleMarkerClick({ lat: 42.6403, lng: 18.1083 }, 'OB Dubrovnik')}
            ></Marker>
            <Marker
              position={{ lat: 46.3081, lng: 16.3378}}
              onClick={() => handleMarkerClick({ lat: 46.3081, lng: 16.3378}, 'OB Varaždin')}
            ></Marker>
            <Marker
              position={{ lat: 44.1194, lng: 15.2319}}
              onClick={() => handleMarkerClick({ lat: 44.1194, lng: 15.2319}, 'OB Zadar')}
            ></Marker>
            

            {open && (
              <InfoWindow
                position={infoWindowPosition}
              >
                <p>{clickedMarkerName}</p>
              </InfoWindow>
            )}
          </Map>
        </div>
      </APIProvider>

      <div id="popis_mjesta">
        {
        (localStorage.token && !pretrazeno) ? (
          <a>
            <button className="map-btn" onClick={pretrazivanje}>
              Pretraži
            </button>
          </a>
        ) : localStorage.token && pretrazeno ? (
          <div class="container">
            {Array.from(new Set(appointments.map(appointments => appointments.appointment_id))).map(uniqueAppointmentsId => (
              <div className={`box ${appointments.find(appointment => appointment.appointment_id === uniqueAppointmentsId).criticalAction ? 'critical-action-box' : ''}`}>
              <form>
                <label for="city">Grad</label>
                <input
                  type="text"
                  value={appointments.find(appointment => appointment.appointment_id === uniqueAppointmentsId).location.locationName}
                  readOnly
                />
                            {appointments.find(appointment => appointment.appointment_id === uniqueAppointmentsId).criticalAction && (
                <button className="critical-action-button">Critical</button>
              )}
                <div class="datetime-row">
                  <div class="date-time-field">
                    <label for="date">Datum</label>
                    <input
                      type="text"
                      id="date"
                      value={formatDate(appointments.find(appointment => appointment.appointment_id === uniqueAppointmentsId).dateAndTime)}
                      readOnly
                    />
                  </div>
            
                  <div class="date-time-field">
                    <label for="time">Vrijeme</label>
                    <input
                      type="time"
                      id="time"
                      value={formatTime(appointments.find(appointment => appointment.appointment_id === uniqueAppointmentsId).dateAndTime)}
                      readOnly
                    />
                  </div>
                </div>
            
                <label for="confirmation">Odaberi potvrdu</label>
                <select id="confirmation" name="confirmation">
                  <option value="confirm1">Potvrda 1</option>
                  <option value="confirm2">Potvrda 2</option>
                  <option value="confirm3">Potvrda 3</option>
                </select>
            
                <button type="submit">Prijavi se</button>
              </form>
            </div>
            
              
            ))}
          </div>
        ) : (
          <div className="dark-background">
            <a href="./prijava">
              <button className="map-btn">Prijavi se <br></br>za odabir termina</button>
            </a>
          </div>
        )
        }
      </div>

    </div>
  );
};

export default YourComponent;