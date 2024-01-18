import React, { useState, useEffect  } from 'react';
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
  var [REG, setREG] = useState(false);
  const [selectedConfirmation, setSelectedConfirmation] = useState('');
  const [username, setUsername] = useState('')
  const [donorData, setDonorData] = useState([]);
  const [notAUser, setNotAUser] =useState('');


  const handleMarkerClick = (markerPosition, markerName) => {
    setInfoWindowPosition(markerPosition);
    setClickedMarkerName(markerName);
    setOpen(true);
  };

 const pretrazivanje = async () => {
  const token = localStorage.getItem("token");
  setPret(true);
  console.log(pretrazeno);

  const decodedToken = jwtDecode(token);
  setUsername(decodedToken.sub)
  if(decodedToken.roles != "user"){
    setNotAUser("ne");
    return 
  }
  try {
    const response = await axios.get(`${baseURL}/user/ActiveAppointments`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log(response.data)

    const locations = response.data.map(appointment => appointment.location);
    setAppointments(response.data)
    setLocations(locations);
    console.log(locations)

  } catch (error) {
    // Obrada greške
  }
  };
  
  useEffect(() => {
    // Set the initial value and trigger onChange
    setSelectedConfirmation('1');
  }, []);
  

  const handleClick = (e, appointment_id, potvrda) => {

    e.preventDefault();
    potvrda = [potvrda]

    //maknuti prazan array sa potvrde kad se slozi baza
    const rezervacija = {
      username,
      appointment_id : parseInt(appointment_id),
      potvrda,
    };
    
    console.log(rezervacija)

      const token = localStorage.getItem("token");
      console.log(rezervacija)
      axios.post(`${baseURL}/user/create`, rezervacija,
        {headers: {
          Authorization: `Bearer ${token}`,
        }}
    ).then((response) => { 
      alert("Uspješna prijava!");
    }).catch((error) => {
          console.log("Batoooooooo error")
          console.error(error);

          // Display dialog with the message
    alert("Već ste prijavljeni na ovaj termin");
      })
    ;};


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
        ) : localStorage.token && pretrazeno && notAUser === "ne" ? (
          <div>
            <h2>Samo korisnik može pretražiti </h2>
            </div>
        ) : localStorage.token && pretrazeno ? (
          <div className="container">
          {appointments.sort((a, b) => (b.criticalAction ? 1 : -1)).map((appointment) => (
          <div className={`box ${appointment.criticalAction ? 'critical-action-box' : ''}`} key={appointment.appointment_id}>
            <form method="post">
            <label htmlFor="city">Mjesto</label>
            <input
              type="text" value={appointment.location.locationName} readOnly/>

            {appointment.criticalAction && (
              <button className="critical-action-button" disabled>Critical</button>
            )}

            <div className="datetime-row">
              <div className="date-time-field">
                <label htmlFor="date">Datum</label>
                <input
                  type="text"
                  id="date"
                  value={formatDate(appointment.dateAndTime)}
                  readOnly
                />
              </div>

              <div className="date-time-field">
                <label htmlFor="time">Vrijeme</label>
                <input
                  type="time"
                  id="time"
                  value={formatTime(appointment.dateAndTime)}
                  readOnly
                />
              </div>
              </div>

              <label htmlFor="confirmation">Odaberi potvrdu</label>
              
              <select id="potvrdaSelect" name="confirmation" onChange={(e) => setSelectedConfirmation(e.target.value)}>
                <option value="1">Kupon za hranu</option>
                <option value="2">Opravdanje izostanka za posao</option>
                <option value="3">Karta za javni prijevoz</option>
                <option value="4">Opravdanje izostanka za školu</option>
              </select>

              <button className="green_btn" type="submit" onClick={(e) => handleClick(e, appointment.appointment_id, selectedConfirmation)}>Rezerviraj</button>
            </form>
          </div>
        ))}

      </div>
        ) : (
          <div>
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