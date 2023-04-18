import React, { FC, useCallback, useEffect, useState } from "react";
import styled from "@emotion/styled";
import { useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import config from "config/config";


interface ApplicationDto {
  id: number;
  title: string;
  image: string;
}

interface Application {
  id: string;
  title: string;
  img: string;
  desc: string;
  logo: string;
}

const ApplicationCat: FC = () => {
  const [applications, setApplications] = useState<Application[]>([] as Application[]);
  const navigate = useNavigate();

  async function getApplications() {
    const response = await fetch(`${config.gantryApiUrl}/applications`)
    if (!response.ok) {
      console.warn(response);
      return;
    }

    return response.json();
  }

  const {data} = useQuery<ApplicationDto[]>(
    ["getApplications"],
    getApplications
  );

  useEffect(() => {
    if (!data) return;

    console.log(data)
    setApplications(data.map(dto => ({
      id: dto.id.toString(),
      desc: dto.image,
      title: dto.title,
      img: "",
      logo: "",
    })));
  }, [data]);

  const onClickHandler = useCallback((applicationId: string) => {
    navigate(`/applicationDetail/${applicationId}`);
  }, []);

  return (
    <>
      <Category>database</Category>
      <Container>
        {applications.length == 0
          ? <MessageBox>
              <MessageItem>
                Empty Applications
              </MessageItem>
            </MessageBox>
          : applications.map(item => {
              return <ItemWrapper key={item.id} onClick={() => onClickHandler(item.id)}>
                <ImageWrapper>{item.img}</ImageWrapper>
                <ItemBottom>
                  <ItemLogoWrapper>
                    <Logo>LO</Logo>
                  </ItemLogoWrapper>
                  <ItemBottomRight>
                    <ItemTitle>{item.title}</ItemTitle>
                    <ItemDesc>{item.desc}</ItemDesc>
                  </ItemBottomRight>
                </ItemBottom>
              </ItemWrapper>
          })
        }
      </Container>
    </>
  );
};

export default ApplicationCat;

const Container = styled.div`
  /* flex-direction: column; */
  width: 100%;
  display: flex;
  flex-wrap: wrap;
`;
const Category = styled.h1`
  border: 1px solid black;
  width: 90px;
  height: 25px;
  text-align: center;
  border-radius: 20px;
`;

const MessageBox = styled.div`
  width: 100%;
  height: 25%;
  display: flex;
  justify-content: center;
  align-items: center;
`
const MessageItem = styled.h1`
  font-weight: bold;
`

const ItemWrapper = styled.div`
  width: 240px;
  height: 300px;
  padding: 30px;
  cursor: pointer;
`;
const ImageWrapper = styled.div`
  width: 100%;
  height: 73%;
  margin-bottom: 5px;
  background-color: gray;
`;

const ItemBottom = styled.div`
  width: 100%;
  height: 25%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const ItemLogoWrapper = styled.div`
  height: 100%;

  width: 20%;
`;
const Logo = styled.div`
  border: 1px solid black;
  border-radius: 50%;
  margin: 4px;
  height: 30px;
  width: 30px;
`;
const ItemBottomRight = styled.div`
  height: 100%;
  width: 80%;

  margin-top: 20px;
`;
const ItemTitle = styled.h1`
  font-weight: bold;
  margin-bottom: 15px;
`;
const ItemDesc = styled.div`
  font-size: 15px;
`;
