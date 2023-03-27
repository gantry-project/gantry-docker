import React, { useCallback, useState } from "react";
import styled from "@emotion/styled";
import { useNavigate } from "react-router-dom";

//style
import Badge from "@mui/material/Badge";
import { FaCartPlus } from "react-icons/fa";

const Navbar = () => {
  const [loginstate, setLoginState] = useState(true);
  const navigate = useNavigate();

  const onClickHandlerHome = useCallback(() => {
    navigate(`/`);
  }, []);

  const onClickHandlerCart = useCallback(() => {
    navigate(`/userAppCart`);
  }, []);
  return (
    <Container>
      <Wrapper>
        <Logo onClick={onClickHandlerHome}>GANTRY-DOCKER</Logo>
        <MainItem>
          <Item>WHY GRANTRY</Item>
          <Item>PRODUCTS</Item>
          <Item>DOCS</Item>
          <Item>LEARN </Item>
          <Item>COMMUNITY </Item>
        </MainItem>
        <RegisterContainer>
          {loginstate ? (
            <UserProfileWrapper>
              <UserProfile>hello oo님</UserProfile>
              <Badge badgeContent={4} color="primary">
                <UserCart onClick={onClickHandlerCart}>
                  <FaCartPlus />
                </UserCart>
              </Badge>
              <UserCart></UserCart>
            </UserProfileWrapper>
          ) : (
            <LoginButton>LOGIN</LoginButton>
          )}
        </RegisterContainer>
      </Wrapper>
    </Container>
  );
};

export default Navbar;

const Container = styled.div`
  height: 60px;
  width: 100%;
  z-index: 999;
  position: sticky;
  top: 0;
`;
const Wrapper = styled.div`
  display: flex;
  height: 100%;
  background-color: aquamarine;
  text-align: center;
  align-items: center;
`;
const Logo = styled.div`
  width: 25%;
  cursor: pointer;
`;

const MainItem = styled.div`
  width: 55%;
  display: flex;
  justify-content: space-between;
`;
const Item = styled.div`
  margin: 10px;
`;
const RegisterContainer = styled.div`
  width: 20%;
  display: flex;
  justify-content: center;
`;
const LoginButton = styled.button`
  border: 0;
  width: 100px;
  height: 40px;
  border-radius: 10px;
  font-size: 15px;
`;
const UserProfile = styled.button`
  border: 0;
  width: 100px;
  height: 40px;
`;
const UserProfileWrapper = styled.div`
  border: 0;
  width: 70%;

  height: 100%;
  display: flex;
  border-radius: 10px;
  justify-content: center;
  align-items: center;
`;
const UserCart = styled.div`
  margin: 10px;
  cursor: pointer;
`;
