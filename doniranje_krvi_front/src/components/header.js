import React from "react";
import axios from "axios";

const naClick = (e) => {
    localStorage.removeItem("token")
    window.location.href = '/'
}

const Header = () => {
    return (
        
        <body className="head">
            <nav>
                <img src="/images/red_drop.png" className="logo"></img>
                <ul>
                    <li><a href="#">Features</a></li>
                    <li><a href="/contact">Contact</a></li>
                    <li><a href="#">Privacy</a></li>
                </ul>
                <div>
                    {
                    (localStorage.token) ?
                    <div className="gumbi">
                        <button className="head-btn" onClick={naClick}>Odjavi se</button>
                        <a href="./user">
                            <img id="logo3" src="/images/user_icon.png" alt=""></img>
                        </a>
                    </div>
                    : 
                    <a href="./prijava">
                        <button className="head-btn">PRIJAVA</button>
                    </a>
                    }
                </div>
            </nav>

            <div className="hero">          
                <div className="head-content">
                        <h1>DARIVANJE KRVI</h1>
                        <p>Hrvatski Crveni križ je nacionalno društvo Crvenog križa u Hrvatskoj.</p>
                        <p>Utemeljitelj dobrovoljnog davanja krvi u Republici Hrvatskoj, promiče
                        dobrovoljno davanje krvi, organizira i provodi akcije davanja krvi.</p>
                        <a href="https://www.hck.hr/" className="head-btn">Pridruži se</a>
                        <br></br>
                        <br></br>
                        <p>Organizacija ima više od 370.000 članova volontera, kao i 550 profesionalaca. U Hrvatskoj je aktivan od 1878. godine.</p>
                        <p>U Hrvatskoj Crveni križ djeluje preko nacionalnog društva Hrvatski Crveni križ,
                        čije je rad reguliran Zakonom o Hrvatskom Crvenom križu
                        i statutom Hrvatskog Crvenog križa</p>
                </div>
            </div>
    
        </body>
        
        
        
    )
}

export default Header;