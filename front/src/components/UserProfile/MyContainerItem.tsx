import React from "react";
import styled from "@emotion/styled";
import {getAuthUser} from "../../api/user";
import axios from "axios";
import config from "../../config/config";
import {useNavigate} from "react-router-dom";
import {useMutation, useQueryClient} from "@tanstack/react-query";

interface Props {
  id: string;
  status: string;
}

const MyContainerItem = ({id, status}: Props) => {
  const queryClient = useQueryClient();


  const {mutate: restartContainer} = useMutation(
    async () => {
      const headers: any = {};
      const token = getAuthUser()?.accessToken;
      if (token) {
        headers["Authorization"] = "Bearer " + token;
      }
      console.log("headers", headers);
      axios.post(
        `${config.gantryApiUrl}/containers/${id}/restart`,
        null,
        {headers: {...headers}}
      ).then(res => {
        return res;
      }).catch(err => {
        console.warn(err);
        alert(err.response.data.detail);
        return;
      });
    },
    {
      onSuccess: data => queryClient.invalidateQueries(["getMyContainers"])
    }
  );


  const {mutate: stopContainer} = useMutation(
    async () => {
      const headers: any = {};
      const token = getAuthUser()?.accessToken;
      if (token) {
        headers["Authorization"] = "Bearer " + token;
      }
      console.log("headers", headers);
      axios.post(
        `${config.gantryApiUrl}/containers/${id}/stop`,
        null,
        {headers: {...headers}}
      ).then(res => {
        return res;
      }).catch(err => {
        console.warn(err);
        alert(err.response.data.detail);
        return;
      });
    },
    {
      onSuccess: data => queryClient.invalidateQueries(["getMyContainers"])
    }
  );


  return (
    <Container>
      <TopItem>
        <TopLeftWrapper>
          <Title> {id} : {status} </Title>
        </TopLeftWrapper>
        <TopRightWrapper>
          <Button onClick={() => restartContainer()}>재실행</Button>
          <Button onClick={() => stopContainer()}>종료</Button>
          <Button>수정</Button>
        </TopRightWrapper>
      </TopItem>
      <BottomItem>기술 스택: springboot, mongodb ...</BottomItem>
    </Container>
  );
};

export default MyContainerItem;

const Container = styled.div`
  width: 800px;
  height: 100px;
  border: 1px solid black;
  border-radius: 1px;
  display: flex;
  justify-content: center;
  cursor: pointer;
  flex-direction: column;
  margin: 15px;
`;

const TopItem = styled.div`
  display: flex;
  height: 30%;

  justify-content: space-between;
  align-items: center;
  padding: 10px;
`;

const Title = styled.h1`
  font-size: 20px;
  width: 300px;
  font-weight: bold;
`;
const BottomItem = styled.div`
  padding: 10px;
  height: 70%;
  width: 100%;
`;

const Id = styled.div`
  margin-left: 20px;
`;
const TopLeftWrapper = styled.div``;
const TopRightWrapper = styled.div``;

const Button = styled.button`
  margin-left: 10px;
`;
