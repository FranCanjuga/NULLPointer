import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const User = () => {
  const token = localStorage.getItem("token");
  const decodedToken = jwtDecode(token);
  const roles = decodedToken.roles;

  const [userData, setUserData] = useState([]);
  const [donorData, setDonorData] = useState([]);
  const [activeApps,setActiveApp] = useState([]);
  const [locReg, setLocationReg] = useState('');
  const [vrijeme, setTime] = useState('');
  const [registrirani, setRegistered] = useState([]);
  const [appDel, setAppDel] = useState('');

 // const [allusersData,setAllUsers] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (roles.includes("admin")) {
          const response = await axios.get(`${baseURL}/admin/unregistered`,{
            headers: {
                Authorization: `Bearer ${token}`,
              },
          });
          setUserData(response.data);
       //   const response2 = await axios.get(`${baseURL}/admin/allDonors`,{
         //   headers: {
           //   Authorization: `Bearer ${token}`,
           // },
        //  });
        //  setAllUsers(response2.data); 

        } 
        else if (roles.includes("user")) {
          const response = await axios.get(
            `${baseURL}/user/profile/${decodedToken.sub}`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
            
            console.log(response.data)
            setDonorData(response.data);
          }

        else if(roles.includes("cross")){
          const response = await axios.get(`${baseURL}/cross/ActiveAppointments`,{
            headers: {
                Authorization: `Bearer ${token}`,
              },
          });
          setActiveApp(response.data);
        }
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };
  
    fetchData();
  }, [token, roles, decodedToken.sub]);


  const approveUser = (username) => {
    axios.post(`${baseURL}/admin/approveDonor/${username}`, null, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        console.log(response.data);
        if (response.status === 200) {
          setUserData((prevUserData) => prevUserData.filter((user) => user.username !== username));
        } else {
          console.error(`Error approving user ${username}: Unexpected response status ${response.status}`);
        }
      })
      .catch((error) => {
        console.error(`Error approving user ${username}:`, error);
      });
  };

  const rejectUser = (username) => {
     axios.post(`${baseURL}/admin/rejectDonor/${username}`,null,{
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(() => {
        setUserData((prevUserData) => prevUserData.filter((user) => user.username !== username));
      })
      .catch((error) => {
        console.error("Error rejecting user:", error);
      });
  };

  //ovo dolje je za cross
  const [bloodTypes,setBloodTypes] = useState([]);
  const [locationID, setLocationID] = useState('');
  const [criticalAction,setCritical] = useState(Boolean);
  const [dateAndTime, setDateAndTime] = useState('');

  const addAppointment = () => {
    // Correct property names to match the backend DTO
    const appointment = {
      bloodTypes: Array.isArray(bloodTypes) ? bloodTypes : [bloodTypes], 
      locationID: parseInt(locationID),
      criticalAction: Boolean(criticalAction),
      dateAndTime : dateAndTime + "T" +vrijeme+":00"
    };
    console.log(appointment);
    const response = axios.post(`${baseURL}/cross/addAppointment`, appointment, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).then(() => {
      console.log(response.data);
    }).catch((error) => {
      console.error(error);
    });
};

const deleteAppointment = () =>{
  const number = parseInt(appDel);
  const response = axios.post(`${baseURL}/cross/AppointmentDelete/${number}`,null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }).then(() => {
    console.log(response.data);
  }).catch((error) => {
    console.error(error);
  });
};

  const registeredForApp = (locReg) => {
    const app = parseInt(locReg)
    console.log(app);
    const response = axios.get(`${baseURL}/cross/RegisteredForAppointment/${app}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).then(() => {
      setRegistered(response.data);
      console.log(response.data);
    }).catch((error) => {
      console.error(error);
    });
  };

  const povratak =() =>{
    window.location.href = '/';
  }
  // za krvi
  const sortBloodTypes = (bloodTypes) => {
    if (Array.isArray(bloodTypes)) {
      return bloodTypes.sort().join('');
    } else if (typeof bloodTypes === 'string') {
      return bloodTypes.split('').sort().join('');
    }
    return bloodTypes;
  };

  const booleanToString = (value) => (value ? 'DA' : 'NE');

  // funkcija koja datum ljepse ispise 
  function napisiDatum(dateOfBirth) {
    if (typeof dateOfBirth !== 'undefined' && typeof dateOfBirth === 'string') {
        var datum  = dateOfBirth.split("-");
        
        if (datum.length === 3) {
            var mj = datum[2].split("T");
            
            if (mj.length === 2) {
                var vrijeme = mj[0] + "." + datum[1] + "." + datum[0] + ".";
                return vrijeme;
            } 
         }      
    }
}

  return (
    <div className="user">
  {roles.includes("admin") ? (
    <div className="reg-wrapper">
      <h1 className="naslov">Pozdrav admin</h1>
      <br />
      <br></br>
      <h2>Svi neverificirani korisnici</h2>
      <br />
      <ul className="user-list">
  { userData.map((user) => (
    <li key={user.donorID} className="user-item">
      <div className="user-info">
        <p className="username">Username : {user.username}</p>
        {user.bloodType && user.bloodType.type && (
          <p className="blood-type">Blood Type : {user.bloodType.type}</p>
        )}
        {user.donor_id && (
          <p className="donor-id">Donor ID : {user.donor_id}</p>
        )}
        {user.location && user.location.locationName && (
          <p className="location">Location : {user.location.locationName}</p>
        )}
      </div>
      <div className="buttons">
      <button
        className="reject-button"
        onClick={() => rejectUser(user.username)}
      >
        Reject
      </button>
      <button
        className="approve-button"
        onClick={() => approveUser(user.username)}
      >
        Approve
      </button>
      </div>
    </li>
  ))}
</ul>
        <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>
        <br></br>
      
    </div>
  ) : roles.includes("user") ? (
    <div className="reg-wrapper">
      <h1 className="naslov">Vaši podaci</h1>
      <br></br>
      <p className="podaci">Korisničko ime : {donorData.username}</p>
      <br></br>
      <p className="podaci">Ime : {donorData.firstName}</p>
      <br></br>
      <p className="podaci">Prezime : {donorData.lastName}</p>
      <br></br>
      <p className="podaci">Broj mobitela : {donorData.phoneNumber}</p>
      <br></br>
      <p className="podaci">Datum rođenja : {napisiDatum(donorData.dateOfBirth)}</p>
      <br></br>
      <p className="podaci">Mjesto : {donorData.city}</p>
      <br></br>
      <p className="podaci">Krvna grupa : {donorData.bloodType}</p>
      <br></br>
      <p className="podaci">Spol : {donorData.gender}</p>
      <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>
    </div>
  ) : roles.includes("institution") ? (
    <div className="reg-wrapper">
      <h1>Korisnicka stranica bolnice</h1>

    </div>
  ) : (
    <div className="reg-wrapper">
      <h1 className="naslov">Pozdrav Crveni križ</h1>
      <br></br>
            <section className="reg-wrapper">
              <header className="naslov2">Appointment</header>
              <form className="reg-form" action="" method="post">
                <div className="column">
                  <div className="reg-input-box">
                    <label htmlFor="bloodTypes"><b>Vrste krvi</b></label>
                    <select className="bloodTypes" required value={bloodTypes} onChange={(e) => setBloodTypes(e.target.value)}>
                      <option>/</option>
                      <option value="A">A</option>
                      <option value="B">B</option>
                      <option value="AB">AB</option>
                      <option value="0">0</option>
                    </select>
                  </div>
                  <div className="reg-input-box">
                    <label htmlFor="locationID"><b>ID lokacije</b></label>
                    <input type="text" placeholder="Upišite ID lokacije" name="locationID"
                      required value={locationID} onChange={(e) => setLocationID(e.target.value)}></input>
                  </div>
                </div>

                <div className="column">
                  <div className="reg-input-box">
                    <label htmlFor="criticalAction"><b>Kritična akcija</b></label>
                    <select className="bloodTypes" required value={criticalAction ? 'DA' : 'NE'} onChange={(e) => setCritical(e.target.value === 'DA')}>
                      <option value="DA">DA</option>
                      <option value="NE">NE</option>
                    </select>
                  </div>
                  <div className="reg-input-box">
                    <label htmlFor="dateAndTime"><b>Datum</b></label>
                    <input type="date" placeholder="Upišite datum" name="dateAndTime"
                      required value={dateAndTime} onChange={(e) => setDateAndTime(e.target.value)}></input>
                  </div>
                </div>

                <div className="column">
                  <div className="reg-input-box">
                    <label htmlFor="vrijeme"><b>Vrijeme</b></label>
                    <input type="time" className="bloodT" placeholder="Upišite vrijeme" required value={vrijeme} onChange={(e) => setTime(e.target.value)}>
                    </input>
                  </div>
                </div>
                <button type="button" className="btn2" onClick={() => addAppointment()}>Dodaj appointment</button>
              </form>
            </section>
      <br></br>
      <br></br>
      <h2>Aktivni sastanci</h2>
      <br></br>
      <div className="user-item-container">
        <ul className="user-item">
            {activeApps.map((app) => (
                <li key={app.locationID} className="user-item">
                    <div className="user-info">
                        <p className="username">Vrste krvi : {sortBloodTypes(app.bloodTypes)}</p>
                        {app.locationID && (
                            <p className="donor-id">ID Lokacije : {app.locationID}</p>
                        )}
                        {app.dateAndTime && (
                            <p className="blood-type">Datum : {napisiDatum(app.dateAndTime)}</p>
                        )}
                        {app.criticalAction && (
                            <p className="location">Kritična akcija : {booleanToString(app.criticalAction)}</p>
                        )}
                    </div>
                </li>
            ))}
        </ul>
    </div>
      <br></br>
      <br></br>
      <section className="reg-wrapper">
            <header>Registrirani za termin</header>
            <form className="reg-form" action="" method="post">
                <div className="column">
                      <div className="reg-input-box">
                        <label htmlFor="uname"><b>ID Lokacije</b></label>
                        <input type="text" placeholder="Upišite ID lokacije" name="uname" 
                        required value={locReg} onChange={(e)=>setLocationReg(e.target.value)}></input>
                      </div>
                    </div>
            </form>
            <button className="btn2" type="button" onClick={() => registeredForApp(locReg)}>Pregledaj termin</button>
        </section>
        <br></br>
       
      <br></br>
      <h2> Svi registrirani</h2>
      {(registrirani) && <ul className="user-list">
        {registrirani.map((user) => (
          <li key={registrirani.username} className="user-item">
            <div className="user-info">
              <p className="username">Username : {registrirani.username}</p>
              {registrirani.bloodType && (
                <p className="blood-type">Krv : {registrirani.bloodType.type}</p>
              )}
              {registrirani.gender && (
                <p className="donor-id">Spol : {registrirani.gender}</p>
              )}
              {registrirani.location && registrirani.location.locationName && (
                <p className="location">Lokacija : {registrirani.location.locationName}</p>
              )}
            </div>
          </li>
        ))}
      </ul>}
      <br></br>
      <br></br>
      <br></br>
      <section className="reg-wrapper">
            <header>Izbrisi appointment</header>
            <form className="reg-form" action="" method="post">
                <div className="column">
                      <div className="reg-input-box">
                        <label htmlFor="uname"><b>ID Appintmenta</b></label>
                        <input type="text" placeholder="Upišite ID appointmenta" name="uname" 
                        required value={appDel} onChange={(e)=>setAppDel(e.target.value)}></input>
                      </div>
                    </div>
            </form>
            <button type="button" className="btn2" onClick={() => deleteAppointment()}>Izbriši appointment</button>
        </section>
      <br></br>
      <button type="button" className="btn3" onClick={() => povratak()}>Vrati se</button>
    </div>
  )}
</div>
  );
}

export default User;

