import React, {useEffect, useState} from "react";
import styled from "@emotion/styled";
import {useNavigate, useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
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

interface Error {
  status: string;
  uri: string;
  message: string;
  detail: string;
}

const ApplicationDetail = () => {
  const [application, setApplication] = useState<Application>({} as Application);
  const { applicationId } = useParams<{ applicationId: string }>();
  const navigate = useNavigate();

  async function getApplication() {
    const response = await fetch(
      `${config.gantryApiUrl}/applications/${applicationId}`
    );

    if (!response.ok) {
      console.warn(response);
      return;
    }

    return response.json();
  }

  const { data } = useQuery<ApplicationDto>(["getApplication"], getApplication);


  useEffect(() => {
    if (!data) return;

    console.log(data)
    setApplication({
      id: data.id.toString(),
      desc: data.image,
      title: data.title,
      img: "",
      logo: "",
    });
  }, [data]);

  const launchApplication = async () => {
    const response = await fetch(
      `${config.gantryApiUrl}/applications/${applicationId}`, {
        method: 'POST',
        headers: {},
        body: JSON.stringify({})
      }
    );

    if (!response.ok) {
      console.warn(response);
      const error = await response.json() as Error;
      alert(error.message);
      return;
    }

    navigate(`/applicationsList`);
  }

  return (
    <Container>
      <DetailItemsContainer>
        <DetailContainer>
          <RightContainer>
            <Logo>로고 이미지</Logo>
          </RightContainer>
          <LeftWrapper>
            <TopItem>
              <Title>{application.title}</Title>
              <LaunchBtn onClick={() => launchApplication()} >LAUNCH</LaunchBtn>
            </TopItem>
            <BottomItem>{application.desc}</BottomItem>
          </LeftWrapper>
        </DetailContainer>
        <SnapchatWrapper>
          <Title>Snashots</Title>
          <SliderWrapp> 그림 들어갈 곳 </SliderWrapp>
        </SnapchatWrapper>
        <ConfigWrapper>
          <Title>Configuration</Title>
          <ConfigText></ConfigText>
        </ConfigWrapper>
      </DetailItemsContainer>
    </Container>
  );
};

export default ApplicationDetail;

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  position: relative;
`;
const DetailItemsContainer = styled.div`
  width: 1320px;
  height: 600px;

  position: absolute;
  top: 40px;
  left: 80px;
`;
const DetailContainer = styled.div`
  height: 35%;
  display: flex;
`;
const RightContainer = styled.div`
  width: 25%;
  height: 100%;
`;
const Logo = styled.div`
  width: 250px;
  height: 200px;
  background-color: gray;
`;
const LeftWrapper = styled.div`
  width: 75%;
`;
const Title = styled.h1`
  font-size: 20px;
  padding-bottom: 5px;
`;
const LaunchBtn = styled.button`
  width: 110px;
  height: 28px;
  border-radius: 10px;

  color: white;
  background-color: skyblue;
  border: none;
  padding: 0;
  margin: 0;
`;
const TopItem = styled.div`
  display: flex;
  justify-content: space-between;
`;
const BottomItem = styled.div``;
const SnapchatWrapper = styled.div`
  height: 30%;
`;
const SliderWrapp = styled.div`
  width: 100%;
  height: 140px;
  background-color: gray;
`;
const ConfigWrapper = styled.div`
  height: 40%;
`;
const ConfigText = styled.div`
  background-color: gray;
  width: 100%;
  height: 160px;
`;
