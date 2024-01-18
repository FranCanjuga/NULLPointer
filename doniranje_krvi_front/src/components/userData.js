import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';


const UserData = () =>{

    const [priznanja, setPriznanje]= useState('');
    const [aktivniSastanci,setAktivniSas] = useState([])
    const [donorRezervations,setDonorRez] = useState([])
    const [app, setApp] = useState('')
    const [message, setMessage] = useState('');
    const token = localStorage.getItem("token");
    const decodedToken = jwtDecode(token);

    // funkcija koja datum ljepse ispise 
  function napisiDatum(date) {
    if (typeof date !== 'undefined' && typeof date === 'string') {
        var datum  = date.split("-");
        
        if (datum.length === 3) {
            var mj = datum[2].split("T");
            
            if (mj.length === 2) {
                var vrijeme = mj[0] + "." + datum[1] + "." + datum[0] + ".";
                return vrijeme;
            } 
         }      
    }
}
//vraca vrijeme
function napisiVrijeme(vrijeme) {
  const dateObject = new Date(vrijeme);
  const timeString = dateObject.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

  return timeString;
}

    useEffect(()=>{
        const dohvatiPriznja = async() =>{
            try{
                const response = await axios.get(`${baseURL}/user/getPotvrdeDonora/${decodedToken.sub}`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                      },
                  });
                  setPriznanje(response.data);
                  console.log(response)
                
            } catch(error){
                console.error("Error fetching user data:", error);
            }

            try{
                
                const response2 = await axios.get(`${baseURL}/user/ActiveAppointments`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                      },
                 }) ;
                 setAktivniSas(response2.data);

            }catch(error){
                console.error("Error fetching user data:", error);
            }
            try{
                const response3 = await axios.get(`${baseURL}/user/ActiveReservations/${decodedToken.sub}`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                      },
                 }) ;
                 setDonorRez(response3.data);
                 console.log(response3)
            }catch(error){
                console.error("Error fetching user data:", error);
            }
        };
        dohvatiPriznja();
    },[token,decodedToken.sub]);

    const izbrisiRez = () => {


     const appointment ={
        username : decodedToken.sub,
        appointmentID : app,
      }

        axios
          .post(`${baseURL}/user/deleteReservation`, appointment, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          })
          .then((response) => {
            console.log(response.data);
            alert("Izbrisana je rezervacija")
            window.location.href ='/userData';
          })
          .catch((error) => {
            if (error.response && error.response.status === 500) {
              console.error("Error 500 - Dodan appointment");
              setMessage('Error 500');
            }
          });
      };

    const povratak =() =>{
        window.location.href = '/user';
      }

      const povijest =() =>{
        window.location.href = '/povijestDoniranja';
      }

    const natragNaUserData =() =>{
        window.location.href = '/userData';
    }

    return(
        <div className="userdata">
  {message ? (
    <div>
        <div className="reg-wrapper">
            <h2>Rezervacija koju brišete ne postoji</h2>
            <br></br>
            <button type="button" className="btn2" onClick={() => natragNaUserData()}>Vrati se</button>
        </div>
    </div>
  ) : (
    <div className="reg-wrapper">
      <h1 className="naslov">Potvrde i rezervacije</h1>
      <br></br>
      <h2>Potvrde</h2>
      <br></br>
      {priznanja === '' ? (
        <div className="reg-wrapper">
          <h2 className="nemanje">Nemate potvrdu o doniranju krvi</h2>
        </div>
      ) : (
        <div>
          <ul className="user-list">
            {priznanja.map((priz) => (
              <li key={priz.id} className="user-item">
                  <div className="user-info">
                    <p className="potvrda">Potvrda : {priz.potvrdaName}</p>
                  </div>
              </li>
            ))}
          </ul>
        </div>
      )}
      <br></br>
      <br></br>
        <div>
          <br></br>
          <h2>Tvoje aktivne rezervacije</h2>
          <br></br>
          {donorRezervations.length === 0 ? (
            <div className="reg-wrapper">
              <h2 className="nemanje">Nemate aktivnih rezervacija</h2>
            </div>
          ) : (
            <div className="reg-wrapper">
                <ul className="user-list">
              {donorRezervations.map((rez) => (
                <li key={rez.appointment_id} className="user-item">
                  <div className="user-info">
                    <p className="blood-type">Datum : {napisiDatum(rez.dateAndTime)}</p>
                    <p className="location">Vrijeme : {napisiVrijeme(rez.dateAndTime)}</p>
                    <p className="donor-id">Lokacija : {rez.locationName}</p>
                  </div>
                </li>
              ))}
              </ul>
            </div>
          )}
          <br></br>
          <br></br>
          <section className="reg-wrapper">
            <header className="naslov2">Brisanje rezervacije</header>
              <form className="reg-form" action="" method="post">
                <div className="column">
                <div className="reg-input-box">
                  <label htmlFor="locationID"><b>Rezervacije</b></label>
                    <select className="mjesto" required onChange={(e) => setApp(e.target.value)}>
                      <option>Odaberite rezervaciju</option>
                      {donorRezervations.map((appointment) => (
                        <option key={appointment.appointmentID} value={appointment.appointmentID}>
                          {appointment.locationName}
                        </option>
                      ))}
                    </select>
                </div>
              </div>
              <button type="button" className="btn2" onClick={() => izbrisiRez()}>Izbrisi rezervaciju</button>
            </form>
          </section>
        </div>
      <br></br>
      <br></br>
      <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>
      <button type="button" className="btn3" onClick={() => povijest()}>Povijest donacija</button>
    </div>
  )}
</div>
    )
}

export default UserData;