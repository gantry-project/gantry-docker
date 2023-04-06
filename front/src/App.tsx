import React from "react";
import { Route, Routes } from "react-router-dom";

//component
import Home from "./pages/Home";
import Layout from "./components/Layout";
import ApplicationsList from "./pages/ApplicationsList";
import ApplicationDetail from "./pages/ApplicationDetail";
import UserContainer from "./pages/UserContainer";

import UserCart from "./pages/UserCart";
import UserProfie from "./pages/User";

function App() {
  return (
    <div>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/applicationsList" element={<ApplicationsList />} />
          <Route path="/applicationDetail" element={<ApplicationDetail />} />

          <Route path="/user" element={<UserProfie />}></Route>
          <Route path="user/container" element={<UserContainer />} />
          <Route path="/userAppCart" element={<UserCart />} />
        </Routes>
      </Layout>
    </div>
  );
}

export default App;
