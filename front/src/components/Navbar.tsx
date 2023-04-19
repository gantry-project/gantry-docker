import React, {useCallback, useState} from "react";
import styled from "@emotion/styled";
import {useNavigate} from "react-router-dom";

//style
import Badge from "@mui/material/Badge";

import Modal from "./Modal";
import {useAuthUser} from "../api/user";
import {MdManageAccounts} from "react-icons/md";
import {Authority} from "../types/UserType";
import {FaUser} from "react-icons/fa";

const Navbar = () => {
  const [modalState, setModalState] = useState(false);
  const navigate = useNavigate();
  const authUser = useAuthUser();

  const onClickHandlerHome = useCallback(() => {
    navigate(`/`);
  }, []);

  const onClickHandlerCart = useCallback(() => {
    navigate(`/userAppCart`);
  }, []);
  // logo 부분에 사이드바 클릭 버튼

  const onClickModal = useCallback(() => {
    setModalState((pre) => !pre);
  }, []);

  const goToLoginPage = useCallback(() => {
    navigate(`/login`);
  }, []);


  return (
    <>
      <Container>
        <Wrapper>
          <Logo onClick={onClickHandlerHome}>GANTRY-DOCKER</Logo>
          <RegisterContainer>
            {authUser ? (
              <UserProfileWrapper>
                <UserProfile onClick={onClickModal}>프로필</UserProfile>
                <Badge badgeContent={4} color="primary">
                  <UserIcon onClick={onClickHandlerCart}>
                    {authUser.authority == Authority.ADMIN? <MdManageAccounts /> : <FaUser/>}
                  </UserIcon>
                </Badge>
                <UserIcon></UserIcon>
              </UserProfileWrapper>
            ) : (
              <LoginButton onClick={goToLoginPage}>LOGIN</LoginButton>
            )}
          </RegisterContainer>
        </Wrapper>
      </Container>
      {modalState ? <Modal setModalState={setModalState} /> : null}
    </>
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
  justify-content: space-between
`;
const Logo = styled.div`
  width: 150px;
  margin: 0px 16px;
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
  width: 150px;
  display: flex;
  justify-content: center;
`;
const LoginButton = styled.button`
  border: 0;
  width: 100px;
  height: 40px;
  border-radius: 10px;
  font-size: 15px;
  cursor: pointer;
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
const UserIcon = styled.div`
  margin: 10px;
  cursor: pointer;
`;
