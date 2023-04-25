import React, {useCallback, useState} from "react";
import styled from "@emotion/styled";
import axios, {AxiosError, AxiosResponse} from "axios";
import config from "config/config";
import {useNavigate} from "react-router-dom";
import {UserPrincipal} from "types/UserType";
import {ErrorResponse} from "types/Error";
import {useAuthUserMutation} from "../../api/user";


const Login = () => {
  const navigate = useNavigate();
  const {login} = useAuthUserMutation();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const submit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const url = `${config.gantryApiServerHost}/auth/token`;
    const user = {
      username: username,
      password: password,
    };

    axios.post(url, user)
      .then((res: AxiosResponse<UserPrincipal>) => {
        const userPrincipal = res.data;
        console.log("userPrincipal", userPrincipal);
        login(userPrincipal);
        navigate("/");
      })
      .catch((err: AxiosError<ErrorResponse>)  => {
        alert(err.response?.data.message)
      });

  }

  const goToSignUpPage = useCallback(() => {
    navigate("/signup");
  }, []);


  return (
    <>
      <Form onSubmit={submit}>
        <Label>사용자명</Label>
        <br/>
        <Input id="username" type="text" placeholder="username" value={username} onChange={e => setUsername(e.target.value)}/>
        <br/>
        <Label>비밀번호</Label>
        <br/>
        <PasswordInput id="password" type="password" placeholder="비밀번호를 입력해주세요." value={password}
                       onChange={e => setPassword(e.target.value)}/>
        <br/>
        <LoginButton>로그인</LoginButton>
        <JoinButton onClick={goToSignUpPage}>회원가입</JoinButton>
      </Form>
    </>
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

export default Login;