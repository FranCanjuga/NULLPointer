import React, {useState} from "react"
import axios from "axios"

const SignIn = () => {

    const[username,setUsername] = useState('')
    const[password,setLozinka]=useState('')
    const handleClick = (e) => {
        e.preventDefault();
        const korisnik = { username, password};
        console.log(korisnik);
    
        axios.post("https://doniranjekrvi.agreeabledune-5b53f232.westeurope.azurecontainerapps.io/auth/login", korisnik,
            {headers: {
                'Content-Type': 'application/json'
            }}
        ).then((response) => {
            const token  =  response.data.jwt;
            console.log(token)
            localStorage.setItem("token", token);
            if (token) {
                axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
            }
            else
                delete axios.defaults.headers.common["Authorization"];
            window.location.href = '/'
        });
    }


    return (
        <form className="signin-form" action="" method="post">
            <div className="container">
                <label htmlFor="uname"><b>Username</b></label>
                <input type="text" placeholder="Upišite username" name="uname" required value={username} onChange={(e)=>setUsername(e.target.value)}></input><br></br>

                <label htmlFor="psw"><b>Lozinka</b></label>
                <input type="password" placeholder="Upišite lozinku" name="psw" required value={password} onChange={(e)=>setLozinka(e.target.value)}></input><br></br>

                <button type="submit" onClick={handleClick}>Prijava</button><br></br>
                <label>Nemaš račun? <a href="./registracija">Registriraj se</a></label>
            </div>

            <div className="container">
                <a href="./"><button type="button" className="cancelbtn">Vrati se</button></a>
            </div>
        </form>
    )
}

export default SignIn;