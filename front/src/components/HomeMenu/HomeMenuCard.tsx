<<<<<<< HEAD
import React, { useCallback } from "react";
import styled from "@emotion/styled";
import { useNavigate } from "react-router-dom";

const HomeMenuCard = () => {
  const navigate = useNavigate();

  const onClickHandler = useCallback(() => {
    navigate(`/dockerList`);
  }, []);

  return (
    <Container onClick={onClickHandler}>
      <Title>DockerList</Title>
    </Container>
  );
};

export default HomeMenuCard;

const Container = styled.div`
  width: 250px;
  height: 200px;
  border: 1px solid black;
  border-radius: 10px;
  cursor: pointer;
`;
const Title = styled.h1`
  font-size: 50px;
`;
=======
import React, { useCallback } from "react";
import styled from "@emotion/styled";
import { useNavigate } from "react-router-dom";

const HomeMenuCard = () => {
  const navigate = useNavigate();

  const onClickHandler = useCallback(() => {
    navigate(`/dockerList`);
  }, []);

  return (
    <Container onClick={onClickHandler}>
      <Title>DockerList</Title>
    </Container>
  );
};

export default HomeMenuCard;

const Container = styled.div`
  width: 250px;
  height: 200px;
  border: 1px solid black;
  border-radius: 10px;
  cursor: pointer;
`;
const Title = styled.h1`
  font-size: 50px;
`;
>>>>>>> 2916dd479f69ed343ba47b234452038c6e8999f3
