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
  const [appDel, setAppDel] = useState('');
  const [appFin, setAppFin] = useState('');
  const [popisGradova, setPopisGradova] = useState([]);

 // const [allusersData,setAllUsers] = useState([]);

 useEffect(() => {

  const fetchData = async () => {
    try {
      if (roles.includes('cross')) {
        const response = await axios.get(`${baseURL}/cross/getLocations`,{
          headers: {
              Authorization: `Bearer ${token}`,
            },
        });
        setPopisGradova(response.data);
      }
    } catch (error) {
      console.error("Error fetching location data:", error);
    }
  }
  fetchData();
}, [token]); 

  useEffect(()=>{
    const mjesto = document.getElementById('odabirMjesta');
    const filterMjesto = document.getElementById('odabirMjestaFiltera');

  // Check if the select element already has options
  if (mjesto && mjesto.childNodes.length === 1) {
      popisGradova.forEach((type) => {
          const option = document.createElement('option');
          option.value = type.location_id;
          option.text = type.locationName;
          mjesto.appendChild(option);
      });
    if (filterMjesto && filterMjesto.childNodes.length==1) {
      popisGradova.forEach((type) => {
        const option = document.createElement('option');
        option.value = type.location_id;
        option.text = type.locationName;
        filterMjesto.appendChild(option)
    });
    }
  }
  }, [popisGradova])

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
  const [filterLoc, setFilterLoc] = useState('');

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
    window.location = window.location.href
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
  window.location = window.location.href
};

  const registeredForApp = async (locReg) => {
    try {
      if (roles.includes('cross')) {
        return axios.get(`${baseURL}/cross/RegisteredForAppointment/${locReg}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
      }
    } catch (error) {
      console.error("Error fetching user data:", error);
    }
  };

  const userdata2 =() =>{
    window.location.href = '/userData';
  }

  const finishAppointment = (usernames, appId) => {
    let body = {
      usernames: usernames,
      appointmentID: parseInt(appId)
    };
  
    console.log(body);
  
    const response = axios.post(`${baseURL}/cross/AppointmentFinished`, body, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(() => {
        window.location.href = '/user';

      })
      .catch((error) => {
        if (error.response && error.response.status === 500) {
            alert("Ne može se završiti appointmen koji je u tijeku")
        } else {
          console.error(error);
        }
      });
  };

  const makeListElementFromDonor = (donor) => {
    const li = document.createElement('li');
    
    const inp = document.createElement('input')
    inp.type = 'checkbox'
    inp.value = donor.username
    inp.classList.add("donor_check")

    const label = document.createElement('label');
    label.appendChild(document.createTextNode(donor.firstName + " " + donor.lastName));

    li.appendChild(label)
    li.appendChild(inp) 
    return li
  }

  const dodajDolaske =  async (registered) => {
    let list = document.getElementById("donor_list");
    list.innerHTML = ''
    if (registered && registered.length>0) {
      let li_list = registered.map(makeListElementFromDonor);
      li_list.forEach((li)=>{
        list.appendChild(li)
      });
    } else {
      list.appendChild(document.createTextNode("Nema dolazaka"))
    }
  } 
  
  const createTableRow = (app) => {
    return (
      <tr key={app.appointment_id}>
        <td>{app.bloodTypes.length === 0 ? ("ALL") :(app.bloodTypes)}</td>
        <td>{napisiDatum(app.dateAndTime)}</td>
        <td>{napisiVrijeme(app.dateAndTime)}</td>
        <td>{app.location.locationName}</td>
        <td>{app.criticalAction ? "YES" : "NO"}</td>
        <td>
          <button id="table_button_finish" href='' value={app.appointment_id} onClick={zavrsiButton}>Završi</button>
        </td>
        <td>
          <button id="table_button_delete" href='' value={app.appointment_id} onClick={izbrisiButton}>Izbriši</button>
        </td>
      </tr>
    );
  };
  

  const deFade = () => {
    document.getElementById('affirmDelete').style.display='none';
    document.getElementById('affirmFinish').style.display='none';
    document.getElementById('fade').style.display='none'
  }

  const izbrisiButton = (event) => {
    setAppDel(event.target.value)
    document.getElementById('affirmDelete').style.display = "block"
    document.getElementById('fade').style.display="block"
  }

  const zavrsiButton = async (event) => {
    let registered = (await registeredForApp(event.target.value)).data;
    setAppFin(event.target.value)
    document.getElementById('affirmFinish').style.display = "block"
    document.getElementById('fade').style.display="block"
    dodajDolaske(registered)
    
  }

  const završiPotvrduDonora = (event) => {
    let checks = document.getElementsByClassName('donor_check')
    checks = Array.from(checks).filter((el)=>el.checked)
    checks = checks.map((ch)=>ch.value)
    finishAppointment(checks, appFin);
  }

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

function napisiDatumSkraceno(dateOfBirth) {
  if (typeof dateOfBirth !== 'undefined' && typeof dateOfBirth === 'string') {
    var datum = dateOfBirth.split("-");

    if (datum.length === 3) {
      var mj = datum[2].split("T");

      if (mj.length === 2) {
        var year = datum[0].slice(2); // Extract last two digits of the year
        var vrijeme = mj[0] + "." + datum[1] + "." + year + ".";
        return vrijeme;
      }
    }
  }
  return "";
}

//vraca vrijeme
function napisiVrijeme(vrijeme) {
  const dateObject = new Date(vrijeme);
  const timeString = dateObject.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

  return timeString;
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
        {user.bloodType && (
          <p className="blood-type">Krvna grupa : {user.bloodType}</p>
        )}
        {user.firstName && (
          <p className="donor-id">Ime i prezime : {user.firstName} {user.lastName}</p>
        )}
        {user.city  && (
          <p className="location">Grad : {user.city}</p>
        )}
        {user.gender  && (
          <p className="gender">Spol : {user.gender}</p>
        )}
        {user.phoneNumber  && (
          <p className="blood-type">Broj mobitela : {user.phoneNumber}</p>
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
      <button type="button" className="btn3" onClick={() => userdata2()}>Potvrde i rezervacije</button>
      <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>
    </div>
  ) : roles.includes("institution") ? (
    <div className="reg-wrapper">
      <h1>Korisnicka stranica bolnice</h1>

    </div>
  ) : (
    // cross
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
                        <label htmlFor="locationID"><b>Mjesto</b></label><br></br>

                        <select id="odabirMjesta" className="mjesto" required value={locationID} onChange={(e)=>setLocationID(e.target.value)}>
                            <option value="">Odaberi mjesto</option>
                        </select>

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
      <ul className="user-list reg-wrapper">
        <div className="reg-form">
          <div className="reg-input-box">
            <label htmlFor="locationID"><b>Filtriraj po mjestu  </b></label>
              <select id="odabirMjestaFiltera" className="mjesto_filtera" required value={filterLoc} onChange={(e)=>setFilterLoc(e.target.value)}>
                <option value="">Odaberi mjesto</option>
              </select>
          </div>
        </div>
          <table class="appointment_table">
            <tr>
              <th>Blood Types</th>
              <th>Date</th>
              <th>Time</th>
              <th>Location</th>
              <th>Critical?</th>
              <th>Završi</th>
              <th>Izbriši</th>
            </tr>
            {filterLoc ? activeApps.filter((app)=> app.location.location_id=== parseInt(filterLoc)).map(createTableRow) : activeApps.map(createTableRow)}
          </table>
            
      </ul>
      <br></br>
      <br></br>
       
      <br></br>
      <button type="button" className="btn3" onClick={() => povratak()}>Vrati se</button>
    </div>
  )} 

    <div id="affirmDelete" class="white_content1">
    
      <label><h2 className="center">Jeste li sigurni?</h2></label>
      <div className="button_container">
        <button className="reject-button" onClick={deFade}>Ne</button>
        <button className="approve-button" onClick={deleteAppointment}>Da</button>
      </div>
    </div>

    <div id="affirmFinish" class="white_content2">
        <button className="close-button" onClick={deFade}>X</button>
        <label><h2>Potvrdi dolaske</h2></label>
        <ul id="donor_list"></ul>
        <div className="button_container2">
          <button className="btn2" onClick={završiPotvrduDonora}>Završi</button>
        </div>
    </div>

    <div id="fade" class="black_overlay"></div>
  </div>
  
  );
}

export default User;

