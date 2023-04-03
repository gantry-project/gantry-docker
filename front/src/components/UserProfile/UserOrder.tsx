import React from "react";
import styled from "@emotion/styled";

const UserOrder = () => {
  return (
    <Container>
      <TopItem>
        <TopLeftWrapper>
          <Title> 이름 : 첫번째 </Title>
        </TopLeftWrapper>
        <TopRightWrapper>
          <Button>실행</Button>
          <Button>종료</Button>
          <Button>수정</Button>
        </TopRightWrapper>
      </TopItem>
      <BottomItem>기술 스택: springboot, mongodb ...</BottomItem>
    </Container>
  );
};

export default UserOrder;

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
