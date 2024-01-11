import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const User = () => {
  const token = localStorage.getItem("token");
  const decodedToken = jwtDecode(token);
  const roles = decodedToken.roles;

  const [userData, setUserData] = useState([]);
  const [donorData, setDonorData] = useState([]);
  const [allusersData,setAllUsers] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (roles.includes("admin")) {
          const response = await axios.get(`${baseURL}/admin/unregistered`,{
            headers: {
                Authorization: `Bearer ${token}`,
              },
          });
          setUserData(response.data);
          const response2 = await axios.get(`${baseURL}/admin/allDonors`,{
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          setAllUsers(response2.data);

        } 
        else if (roles.includes("user")) {
          const response = await axios.get(
            `${baseURL}/user/profile`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
              body:{
                username : decodedToken.sub,
              }
            }
          );
            
            console.log(response.data)
            setDonorData(response.data);
          }
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };
  
    fetchData();
  }, [token, roles, decodedToken.sub]);


  const approveUser = (username) => {
    const response = axios.post(`${baseURL}/admin/approveDonor`,{
      headers: {
          Authorization: `Bearer ${token}`,
        },
      params:{
        username : username,
      },
    })
      .then(() => {
        console.log(response.data);
        setUserData((prevUserData) => prevUserData.filter((user) => user.username !== username));
      })
      .catch((error) => {
        
        console.error(`Error approving user ${username}:`, error);
      });
  };

  const rejectUser = (username) => {
    const response = axios.post(`${baseURL}/admin/rejectDonor`,{
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body :{
        username : username
      },
    })
      .then(() => {
        console.log(response.data);
        setUserData((prevUserData) => prevUserData.filter((user) => user.username !== username));
      })
      .catch((error) => {
        console.error("Error rejecting user:", error);
      });
  };

  const addAppointment =() => {
    const response =  axios.post(`${baseURL}/cross/addAppointment`,{
      headers: {
          Authorization: `Bearer ${token}`,
        }

    }).then(()=>{
      console.log(response.data)
      
    }).catch((error) => {
        
      console.error(error);
    });
  };

  const activeApp = async() => {

  };

  const registeredForApp =async() => {

  };

  const povratak =() =>{
    window.location.href = '/';
  }

  return (
    <div>
  {roles.includes("admin") ? (
    <div>
      <h1>Pozdrav admin</h1>
      <br />
      <button onClick={() => povratak()}>Vrati se</button>
      <br></br>
      <br></br>
      <h2>Svi donori</h2>
      <ul>
        {allusersData.map((user) => (
          <li key ={user.donorID}>
            <p>Username: {user.username}</p>
            <p>Blood Type: {user.bloodType}</p>
            <p>Donor ID: {user.donorID}</p>
            <p>Town: {user.town}</p>
            <br></br>
          </li>
        ))

        }
      </ul>
      <br></br>
      <h2>Svi neverificirani korisnici</h2>
      <br />
      <ul>
        {userData.map((user) => (
          <li key={user.donorID}>
            <p>Username: {user.username}</p>
            <p>Blood Type: {user.bloodType}</p>
            <p>Donor ID: {user.donorID}</p>
            <p>Town: {user.town}</p>
            <button onClick={() => rejectUser(user.username)}>Reject</button>{' '}
            <button onClick={() => approveUser(user.username)}>Approve</button>
            <br></br>
            <br></br>
          </li>
        ))}
      </ul>
    </div>
  ) : roles.includes("user") ? (
    <div>
      <h2>Vaši podaci</h2>
      <br></br>
      <p>Korisničko ime: {donorData.username}</p>
      <p>Ime: {donorData.firstName}</p>
      <p>Prezime: {donorData.lastName}</p>
      <p>Broj mobitela: {donorData.phoneNumber}</p>
      <p>Datum rođenja: {donorData.dateOfBirth}</p>
      <p>Mjesto: {donorData.city}</p>
      <p>Krvna grupa: {donorData.bloodType}</p>
    </div>
  ) : roles.includes("institution") ? (
    <div>
      <h1>Korisnicka stranica bolnice</h1>

    </div>
  ) : (
    <div>
      <h1>{console.log(donorData)}</h1>
      <h1>Pozdrav Crveni križ</h1>
      <br></br>
      <button onClick={() => addAppointment()}>Dodaj sastanak donacija</button>
      <br></br>
      <button onClick={() => activeApp()}>Aktivni sastanci donacija</button>
      <br></br>
      <button onClick={() => registeredForApp()}>Registrirani za sastanke</button>
      <br></br>

    </div>
  )}
</div>
  );
}

export default User;

