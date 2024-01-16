import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const History = () =>{

    const povratak =() =>{
        window.location.href = '/userData';
      }

    return(
    <div className="history">
        <div className="reg-wrapper">
            <h1 className="naslov">Vaša povijest doniranja</h1>
            <br></br>
            <br></br>
            <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>  
        </div>
    </div>
    )
}
export default History;