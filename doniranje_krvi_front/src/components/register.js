import axios from "axios";
import { useState } from "react";

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const Register = () => {

    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')
    const [dateOfBirth, setDateOfBirth] = useState('')
    const [city, setCity] = useState('')
    const [address,setAddress] = useState('')
    const [bloodType,setBloodType] = useState('')

    const handleClick = (e) => {
        e.preventDefault();
        const korisnik = { username, password, firstName, lastName, phoneNumber, dateOfBirth, city, address, bloodType };
        console.log(korisnik);
    
        axios.post(`${baseURL}/auth/register`, korisnik,
            {headers: {
                'Content-Type': 'application/json'
            }}
        ).then((response) => {
            const token  =  response.data.jwt;
            console.log(token)
            localStorage.setItem("token", token);
            if (token && token!==undefined) {
                axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
            }
            else
                delete axios.defaults.headers.common["Authorization"];
            window.location.href = '/'
        });
    }

    return (
        <form className="register-form" action="" method="post">
            <div className="container">
                <label htmlFor="uname"><b>Username</b></label>
                <input type="text" placeholder="Upišite username" name="uname" required value={username} onChange={(e)=>setUsername(e.target.value)}></input><br></br>

                <label htmlFor="psw"><b>Lozinka</b></label>
                <input type="password" placeholder="Upišite lozinku" name="psw" required value={password} onChange={(e)=>setPassword(e.target.value)}></input><br></br>

                <label htmlFor="ime"><b>Ime</b></label>
                <input type="text" placeholder="Upišite ime" name="ime" required value={firstName} onChange={(e)=>setFirstName(e.target.value)}></input><br></br>

                <label htmlFor="prezime"><b>Prezime</b></label>
                <input type="text" placeholder="Upišite prezime" name="prezime" required value={lastName} onChange={(e)=>setLastName(e.target.value)}></input><br></br>

                <label htmlFor="br"><b>Broj mobitela</b></label>
                <input type="text" placeholder="Upišite broj mobitela" name="br" required value={phoneNumber} onChange={(e)=>setPhoneNumber(e.target.value)}></input><br></br>

                <label htmlFor="dt"><b>Datum rodenja</b></label>
                <input type="date" placeholder="Upišite datum rodenja" name="dt" required value={dateOfBirth} onChange={(e)=>setDateOfBirth(e.target.value)}></input><br></br>

                <label htmlFor="mj"><b>Mjesto</b></label>
                <input type="text" placeholder="Upišite mjesto" name="mj" required value={city} onChange={(e)=>setCity(e.target.value)}></input><br></br>

                <label htmlFor="add"><b>Adresa</b></label>
                <input type="text" placeholder="Upišite adresu" name="add" required value={address} onChange={(e)=>setAddress(e.target.value)}></input><br></br>

                <label htmlFor="krv"><b>Krvna grupa</b></label>
                <input type="text" placeholder="Upišite krvnu grupu" name="krv" required value={bloodType} onChange={(e)=>setBloodType(e.target.value)}></input><br></br>

                <button type="submit" onClick={handleClick}>Registriraj se</button><br></br>
            </div>


            <div className="container">
                <a href="./prijava"><button type="button" className="cancelbtn">Vrati se</button></a>
            </div>
        </form>
    )
}

export default Register;