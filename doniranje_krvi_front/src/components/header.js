import React from "react";
import axios from "axios";
import { useState } from 'react';




const Header = () => {
    var [REG, setREG] = useState(false);

    const scrollToMap = () => {
        const mapSection = document.getElementById('map');
    
        if (mapSection) {
          const offsetTop = mapSection.getBoundingClientRect().top + window.scrollY;
    
          window.scrollTo({
            top: offsetTop,
            behavior: 'smooth',
          });
        }
      }; 
      
    const naClick = (e) => {
    setREG(false)
    localStorage.removeItem("token")
    window.location.href = '/'
}
    return (
        
        <body className="head">
            <nav>
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill-opacity="0.8" d="M0,192L120,176C240,160,480,128,720,144C960,160,1200,224,1320,256L1440,288L1440,0L1320,0C1200,0,960,0,720,0C480,0,240,0,120,0L0,0Z"></path></svg>
            <svg id="svg2" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill-opacity="0.8" d="M0,192L120,176C240,160,480,128,720,144C960,160,1200,224,1320,256L1440,288L1440,0L1320,0C1200,0,960,0,720,0C480,0,240,0,120,0L0,0Z"></path></svg>
                
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
                        <p>Organizacija ima više od 370.000 članova volontera, kao i 550 profesionalaca. U Hrvatskoj je aktivan od 1878. godine.</p>
                        <a href="#" className="head-btn" onClick={scrollToMap}>Pridruži se</a>
                </div>
            </div>
    
        </body>
        
        
        
    )
}

export default Header;