import React, { useCallback } from "react";
import styled from "@emotion/styled";
import { useNavigate } from "react-router-dom";

const Modal = () => {
  const navigate = useNavigate();
  const onClickHandler = useCallback(() => {
    navigate(`/userContainer`);
  }, []);
  return (
    <Container>
      <UserInfo>
        <UserNmae>로그인 이름</UserNmae>
        <UserLi>마이페이지</UserLi>
        <UserLi onClick={onClickHandler}>나의 컨테이너</UserLi>
      </UserInfo>
      <UserLogout>로그아웃</UserLogout>
    </Container>
  );
};

export default Modal;

const Container = styled.div`
  position: absolute;
  right: 90px;
  top: 50px;
  border: 1px solid black;
  background-color: whitesmoke;
  width: 150px;
  height: 150px;
  display: flex;
  flex-direction: column;
  z-index: 1000;
  padding: 3px;
  border-radius: 5%;
`;
const UserInfo = styled.ul`
  width: 100%;
  height: 80%;
  background-color: white;
`;
const UserNmae = styled.li`
  padding-bottom: 18px;
  padding-top: 10px;
  border-bottom: 1px solid black;
`;
const UserLi = styled.li`
  width: 100%;
  padding-top: 10px;
  cursor: pointer;
`;
const UserLogout = styled.button`
  border: none; /* 버튼의 테두리 제거 */
  outline: none; /* 클릭 시 포커스 표시 제거 */
  background: none; /* 버튼의 배경색 제거 */
  padding: 0; /* 버튼의 내부 여백 제거 */
  margin: 0; /* 버튼의 바깥 여백 제거 */
  cursor: pointer;
`;
