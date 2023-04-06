import React from "react";
import { Route, Routes } from "react-router-dom";

//component
import Home from "./pages/Home";
import Layout from "./components/Layout";
import ApplicationsList from "./pages/ApplicationsList";
import UserCart from "./pages/UserCart";
import UserProfie from "./pages/User";
import ApplicationDetail from "./pages/ApplicationDetail";

function App() {
  return (
    <div>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/applicationsList" element={<ApplicationsList />} />
          <Route path="/applicationDetail" element={<ApplicationDetail />} />

          <Route path="/user" element={<UserProfie />}></Route>
          <Route path="/userAppCart" element={<UserCart />} />
        </Routes>
      </Layout>
    </div>
  );
}

export default App;
