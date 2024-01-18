import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const History = () =>{

    const [povijest,setPovijest] = useState([])
    const token = localStorage.getItem("token");
    const decodedToken = jwtDecode(token);

    const povratak =() =>{
        window.location.href = '/userData';
      }
    
      useEffect(()=>{
        const dohvatiPovijest = async() =>{
            try{
                const response = await axios.get(`${baseURL}/user/AllDonorReservations/${decodedToken.sub}`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                      },
                  });
                  setPovijest(response.data);
                
            } catch(error){
                console.error("Error fetching user data:", error);
            }

        };
        dohvatiPovijest();
    },[token,decodedToken.sub]);

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

//vraca vrijeme
function napisiVrijeme(vrijeme) {
    const dateObject = new Date(vrijeme);
    const timeString = dateObject.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  
    return timeString;
  }
    
    
    return(
    <div className="history">
        <div className="reg-wrapper">
            <h1 className="naslov">Vaša povijest doniranja</h1>
            <br></br>
            <br></br>
            {povijest.length ===0 ? (
                <div className="reg-wrapper">
                    <h2 className="nemanje">Nemate povijest doniranja krvi</h2>
                </div>
            ) : (
                    <ul className="user-list">
                            {povijest.map((pov) => (
                        <li key={pov.appointmentID} className="user-item">
                            <div className="user-info">
                                <p className="blood-type">Datum : {napisiDatum(pov.dateAndTime)}</p>
                                <p className="dosao">Vrijeme : {napisiVrijeme(pov.dateAndTime)}</p>
                                <p className="donor-id">Lokacija : {pov.locationName}</p>
                            </div>
                        </li>
                        ))}
                    </ul>
            )}
            <br></br>
            <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>  
        </div>
    </div>
    )
}
export default History;