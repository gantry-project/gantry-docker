import React, { useState, useCallback, ChangeEvent } from "react";
import styled from "@emotion/styled";
import { useQuery, useMutation } from "@tanstack/react-query";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import userApi from "../../api/user";
import { UserProps } from "../../types/UserType";

// export interface UserProps {
//   username: string;
//   email: string;
//   password: string;
//   confirmPassword: string;
// }

const Signup = () => {
  const [confirmPassword, setConfirmPassword] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [passwordError, setPasswordError] = useState(false);
  const navigate = useNavigate();

  // async function postSignup(signupData: UserProps) {
  //   const res = await axios.post(
  //     "http://localhost:8080/api/v1/users",
  //     signupData
  //   );
  //   return res;
  // }

  const postMutation = useMutation(
    (signupData: UserProps) => userApi.postRegister(signupData),
    {
      onSuccess: (data) => {
        console.log("Server response:", data);
        navigate("/login");
      },
    }
  );

  const onChangePasswordCheck = useCallback(
    (e: ChangeEvent<HTMLInputElement>) => {
      setConfirmPassword(e.target.value);
      setPasswordError(e.target.value !== password);
    },
    [password]
  );

  const onChangeEmail = (e: ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };
  const onChangeUsername = (e: ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  };
  const onChangePassword = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const onSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      if (password !== confirmPassword) {
        setPasswordError(true);
        return;
      }
      const postSignupData = {
        username: username,
        email: email,
        password: password,
        confirmPassword: confirmPassword,
      };

      postMutation.mutate(postSignupData);
    },
    [email, username, password, confirmPassword, postMutation]
  );

  return (
    <Container>
      <SignupWrapper>
        <InputWrapper onSubmit={onSubmit}>
          <label>Email</label>
          <Input
            name="email"
            placeholder="email"
            type="email"
            value={email}
            onChange={onChangeEmail}
            required
          />
          <label>username</label>
          <Input
            name="username"
            placeholder="username"
            onChange={onChangeUsername}
            value={username}
            required
          />
          <label>password</label>
          <Input
            name="password"
            placeholder="password"
            type="password"
            value={password}
            required
            onChange={onChangePassword}
          />
          <label>passwordCheck</label>
          <Input
            name="checkPassword"
            type="password"
            placeholder="checkPassword"
            value={confirmPassword}
            required
            onChange={onChangePasswordCheck}
          />
          {passwordError ? (
            <Error>비밀번호가 일치하지않습니다.</Error>
          ) : (
            <NoError>비밀번호가 일치합니다.</NoError>
          )}
          <Button>회원가입</Button>
        </InputWrapper>
      </SignupWrapper>
    </Container>
  );
};

export default Signup;

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const SignupWrapper = styled.div`
  width: 500px;
  height: 500px;
  border: 1px solid black;
  border-radius: 5%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const InputWrapper = styled.form`
  width: 260px;
  height: 400px;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Input = styled.input`
  width: 250px;
  height: 20px;
  margin-top: 8px;
  margin-bottom: 20px;
`;

const Button = styled.button`
  width: 150px;
  height: 30px;
  margin: 30px;
`;
const Error = styled.div`
  color: red;
`;

const NoError = styled.div`
  color: blue;
`;
