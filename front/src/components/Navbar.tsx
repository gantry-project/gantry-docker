import React from "react";
import styled from "styled-components";

const Navbar = () => {
  return (
    <Container>
      <Wrapper>
        <Logo>GANTRY-DOCKER</Logo>
        <MainItem>
          <Item>WHY GRANTRY</Item>
          <Item>PRODUCTS</Item>
          <Item>DOCS</Item>
          <Item>LEARN </Item>
          <Item>COMMUNITY </Item>
        </MainItem>
        <RegisterContainer>
          <LoginButton>LOGIN</LoginButton>
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
`;
const LoginButton = styled.button`
  border: 0;
  width: 100px;
  height: 40px;
  border-radius: 10px;
  font-size: 15px;
`;
