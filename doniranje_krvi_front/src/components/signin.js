import React, {useState} from "react"
import axios from "axios"
const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const SignIn = () => {

    const[username,setUsername] = useState('')
    const[password,setLozinka]=useState('')
    const handleClick = (e) => {
        e.preventDefault();
        const korisnik = { username, password};
        console.log(korisnik);
    
        axios.post(`${baseURL}/auth/login`, korisnik,
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
    <body className="signin">

    <div className="wrapper">   
        <form action="" method="post">
            <h1>Login</h1>
                <div className="input-box">
                    <input type="text" placeholder="Username" name="uname"
                    required value={username} onChange={(e)=>setUsername(e.target.value)}></input>
                    <i class='bx bxs-user'></i>
                </div>
                <div className="input-box">
                    <input type="password" placeholder="Password" name="psw" 
                    required value={password} onChange={(e)=>setLozinka(e.target.value)}></input>
                    <i class='bx bxs-lock-alt'></i>
                </div>

                <div className="remember-forgot">
                    <label><input type="checkbox"></input> Zapamti me</label>
                    <a href="#">Zaboravljena lozinka?</a>
                </div>

                <button type="submit" className="btn" onClick={handleClick}>
                Prijava</button>
                
                <div className="register-link">
                    <p>Nemaš račun? <a href="./registracija">Registriraj se</a></p>
                </div>
        </form>
        </div>

    </body>
    )
}

export default SignIn;