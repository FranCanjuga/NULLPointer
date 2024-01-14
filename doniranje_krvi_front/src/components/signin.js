import React, {useState} from "react"
import axios from "axios"
const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const SignIn = () => {

    const[username,setUsername] = useState('')
    const[password,setLozinka]=useState('')
    const [message, setMessage] = useState('');
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
        }).catch((error) => {
            if (error.response && error.response.status === 500) {
              console.error("Error 500 - Dodan appointment");
                setMessage('Error 500');
            } else {
              console.error(error);
            }
          });
    }

    const povratak =() =>{
        window.location.href = '/';
      }

    const Reg = () =>{
        window.location.href='/registracija';
    }


    return (
    <body className="signin">
         {message ? (
        <div className="wrapper">
            <h2>Korisničko ime ili lozinka su pogrešni</h2>
            <br></br>
            <button type="button" className="btn2" onClick={() => Reg()}>Registriraj se</button>
        </div>
      ) : (
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
                
                <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>
        </form>
        </div>
      )}
    </body>
    )
}

export default SignIn;