import React from "react";
import axios from "axios";

const naClick = (e) => {
    localStorage.removeItem("token")
    window.location.href = '/'
}


const Header = () => {
    return (
        
        <header id="header_h">
            <img id="logo" src="/images/red_drop.png" alt=""></img>
            <h1 className="naslov">STRANICA ZA DONACIJU KRVI</h1>
            <img id="logo2" src="/images/bell.jpg" alt=""></img>
            {
            (localStorage.token) ?
            <div className="gumbi">
                <button className="odjava_button" onClick={naClick}>Odjavi se</button><br></br>
                <a href="./user">
                    <img id="logo3" src="/images/user_icon.png" alt=""></img>
                </a>
            </div>
            : 
            <a href="./prijava">
                <button className="login_button">PRIJAVA</button>
            </a>
            }
        </header>
    )
}

export default Header;