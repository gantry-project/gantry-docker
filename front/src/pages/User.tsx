import React from "react";
import styled from "@emotion/styled";
//components
import MyContainerList from "../components/UserProfile/MyContainerList";

const User = () => {
  return (
    <UserProfileWrapper>
      <TopWrapper> 위에 </TopWrapper>
      <BottomWrapper>
        <MyContainerList />
      </BottomWrapper>
    </UserProfileWrapper>
  );
};

export default User;

const UserProfileWrapper = styled.div`
  width: 100%;
  height: 100vh;
  background-color: whitesmoke;
`;
const TopWrapper = styled.div`
  height: 50px;
`;
const BottomWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;