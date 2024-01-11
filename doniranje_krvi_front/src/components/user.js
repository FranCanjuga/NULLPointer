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
        } else {

          
          const response = await axios.get(
            `${baseURL}/user/profile`,
            {
              params: {
                user: decodedToken.sub
              },
              headers: {
                  Authorization: `Bearer ${token}`,
              }
            },
          );
        
        console.log(response.data)
        setDonorData(response.data);
        }

      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };
  
    fetchData();
  }, [token, roles]);


  const approveUser = (username) => {


    axios.post(`${baseURL}/admin/approveDonor`,{username : username},{
      headers: {
        Authorization: `Bearer ${token}`,
    },
  })
      .then(() => {
        console.log(`User ${username} approved successfully`);
        setUserData((prevUserData) => prevUserData.filter((user) => user.username !== username));
      })
      .catch((error) => {
        
        console.error(`Error approving user ${username}:`, error);
      });
  };

  const rejectUser = (username) => {
    axios.post(`${baseURL}/admin/rejectDonor`, { username })
      .then(() => {
        console.log("User rejected successfully");
      })
      .catch((error) => {
        console.error("Error rejecting user:", error);
      });
  };


  return (
    <div>
      

      {roles.includes("admin") ? (
        <div>
            <h1>Pozdrav admin</h1>
            <br></br>
            <h1>Svi neverificirani korisnici</h1>
            <br></br>
            <ul>
            {userData.map((user) => (
              <li key={user.donorID}>
                <p>Username: {user.username}</p>
                <p>Blood Type: {user.bloodType}</p>
                <p>Donor ID: {user.donorID}</p>
                <p>Town: {user.town}</p>
                <button onClick={() => rejectUser(user.username)}>Reject</button>{' '}
                <button onClick={() => approveUser(user.username)}>Approve</button>
              </li>
            ))}
            </ul>
        
        </div>
      ) : (
        <div>
          <h2>Vaši podaci</h2>
          <p>Korisničko ime: {donorData.username}</p>
          <p>Ime: {donorData.firstName}</p>
          <p>Prezime: {donorData.lastName}</p>
          <p>Broj mobitela: {donorData.phoneNumber}</p>
          <p>Datum rođenja: {donorData.dateOfBirth}</p>
          <p>Mjesto: {donorData.city}</p>
          <p>Adresa: {donorData.address}</p>
          <p>Krvna grupa: {donorData.bloodType}</p>
        </div>
      )}
    </div>
  );
}

export default User;

