import React from "react";
const Header = () => {
    return (
        
        <header id="header_h">
            <img id="logo" src="/images/red_drop.png"></img>
            <h1 className="naslov">STRANICA ZA DONACIJU KRVI</h1>
            <img id="logo2" src="/images/bell.jpg"></img>
            {
            (localStorage.token) ?
            <a href="./user">
                <img id="logo3" src="/images/user_icon.png"></img>
            </a>
            : 
            <a href="./prijava">
                <button className="login_button">PRIJAVA</button>
            </a>
            }
        </header>
    )
}

export default Header;