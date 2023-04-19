import React from "react";
import styled from "@emotion/styled";
import { useNavigate } from "react-router-dom";

const UserLogin = () => {
  const navigate = useNavigate();
  const handleButtonClick = () => {
    navigate("/signup");
  };

  return (
    <Form>
      <Label>이메일</Label>
      <br />
      <Input type="text" placeholder="example@naver.com" />
      <br />
      <Label>비밀번호</Label>
      <br />
      <PasswordInput type="password" placeholder="비밀번호를 입력해주세요." />
      <br />
      <JoinButton onClick={handleButtonClick}>회원가입</JoinButton>
      <LoginButton type="submit">로그인</LoginButton>
    </Form>
  );
};

const Form = styled.form`
  text-align: center;
  font-size: 20px;
  border: 3px solid;
  margin: 50px auto;
  padding: 30px;
  width: 30%;
`;
const Label = styled.label`
  display: block;
  font-size: 2rem;
  margin-top: 15px;
`;
const Input = styled.input`
  width: 60%;
  height: 40px;
  border: 2px solid;
`;
const PasswordInput = styled(Input)`
  margin-top: 5px;
  margin-bottom: 40px;
`;
const ButtonsWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
`;
const JoinButton = styled.button`
  font-size: 20px;
  padding: 10px 20px;
`;
const LoginButton = styled.button`
  font-size: 20px;
  padding: 10px 20px;
`;
export default UserLogin;
