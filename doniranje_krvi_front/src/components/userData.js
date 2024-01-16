import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';


const UserData = () =>{

    const [priznanja, setPriznanje]= useState('');
    const [aktivniSastanci,setAktivniSas] = useState([])
    const [donorRezervations,setDonorRez] = useState([])
    const [appID, setAppId] = useState('')
    const [username,setUsername] = useState('')
    const token = localStorage.getItem("token");
    const decodedToken = jwtDecode(token);


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

    useEffect(()=>{
        const dohvatiPriznja = async() =>{
            try{
                const response = await axios.get(`${baseURL}/user/getPotvrdeDonora/${decodedToken.sub}`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                      },
                  });
                  setPriznanje(response.data);

                
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
                 console.log(response2.data);
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
                 console.log(response3.data);
            }catch(error){
                console.error("Error fetching user data:", error);
            }
        };
        dohvatiPriznja();
    },[token,decodedToken.sub]);

    const izbrisiRez =() =>{

    }

    const povratak =() =>{
        window.location.href = '/user';
      }

      const povijest =() =>{
        window.location.href = '/povijestDoniranja';
      }

    return(
        <div className="userdata">
            <div className="reg-wrapper">
                <h1 className="naslov">Potvrde i rezervacije</h1>
                <br></br>
                <h2>Potvrde</h2>
                <br></br>
                {priznanja==='' ? (
                    <div className="reg-wrapper">
                        <h2 className="nemanje">Nemate potvrdu o doniranju krvi</h2>
                    </div>
                ) :(
                    <div>
                        <ul className="user-list">
                                {priznanja
                                .map((priz) => (
                                <li key={priz.potvrda_id} className="user-item">
                                    <div className="user-info">
                                    <p className="username">Potvrda : {priz.namePotvrda}</p>
                                    
                                    </div>
                                </li>
                                    ))}
                        </ul>
                    </div>
                )}
                <br></br>
                <br></br>
                <h2>Aktivni sastanci darivanja krvi</h2>
                <br></br> 
                {aktivniSastanci.length === 0 ? (
                    <div className="reg-wrapper">
                    <h2 className="nemanje">Nemate aktivnih sastanaka</h2>
                </div>
                ):(
                    <div>
                        <ul className="user-list">
                        {aktivniSastanci.map((akSas) => (
                                        akSas.bloodTypes && akSas.bloodTypes.length > 0 ? (
                                            <li key={akSas.appointment_id} className="user-item">
                                            <div className="user-info">
                                                <p className="username">Vrste krvi : {akSas.bloodTypes.join(', ')}</p>
                                                <p className="location">Kritična akcija : {booleanToString(akSas.criticalAction)}</p>
                                                <p className="blood-type">Datum : {napisiDatum(akSas.dateAndTime)}</p>
                                                <p className="donor-id">Lokacija : {akSas.location.locationName}</p>
                                            </div>
                                            </li>
                                        ) : null
                                        ))}
                        </ul>
                        <br></br>
                        <h2>Tvoje aktivne rezervacije</h2>
                        <br></br>
                        {donorRezervations.length === 0 ? (
                            <div className="reg-wrapper">
                            <h2 className="nemanje">Nemate aktivnih rezervacija</h2>
                        </div>
                        ):(
                            <div className="reg-wrapper">
                                {donorRezervations.map((rez) => (
                                            <li key={rez.appointment_id} className="user-item">
                                            <div className="user-info">
                                                <p className="username">Došao : {rez.came}</p>
                                                <p className="blood-type">Datum : {napisiDatum(rez.dateAndTime)}</p>
                                                <p className="donor-id">Lokacija : {rez.location.locationName}</p>
                                            </div>
                                            </li>
                                        ))}
                            </div>
                        )}
                        <br></br>
                        <br></br>
                        <section className="reg-wrapper">
                                    <header className="naslov2">Brisanje rezervacije</header>
                                    <form className="reg-form" action="" method="post">
                                        <div className="column">
                                        <div className="reg-input-box">
                                                <label htmlFor="locationID"><b>Username</b></label>
                                                <input type="text" placeholder="Upišite username" name="locationID"
                                                required value={username} onChange={(e) => setUsername(e.target.value)}></input>
                                            </div>
                                            <div className="reg-input-box">
                                                <label htmlFor="locationID"><b>Appointment ID</b></label>
                                                <input type="text" placeholder="Upišite ID appointmenta" name="locationID"
                                                required value={appID} onChange={(e) => setAppId(e.target.value)}></input>
                                            </div>
                                        </div>
                                        <button type="button" className="btn2" onClick={() => izbrisiRez()}>Izbrisi rezervaciju</button>
                                    </form>
                        </section>
                    </div>
                    
                )} 
                <br></br>
                <br></br>
                <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>    
                <button type="button" className="btn3" onClick={() => povijest()}>Povijest donacija</button>               
            </div>
            
            
        </div>
    )
}

export default UserData;