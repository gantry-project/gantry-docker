import React from "react";
import {Route, Routes} from "react-router-dom";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";

//component
import Home from "./pages/Home";
import Layout from "./components/Layout";
import ApplicationsList from "./pages/ApplicationsList";
import ApplicationDetail from "./pages/ApplicationDetail";
import UserContainer from "./pages/UserContainer";
// 보류
import UserCart from "./pages/UserCart";
import UserProfile from "./pages/User";

import Signup from "./pages/user/Signup";
import Login from "./pages/user/Login";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />
          <Route path="/applicationsList" element={<ApplicationsList />} />
          <Route path="/applicationDetail/:applicationId" element={<ApplicationDetail />}/>
          <Route path="/user" element={<UserProfile />}></Route>
          <Route path="/user/container" element={<UserContainer />} />
          <Route path="/userAppCart" element={<UserCart />} />
        </Routes>
      </Layout>
      <ReactQueryDevtools />
    </QueryClientProvider>
  );
}

export default App;
