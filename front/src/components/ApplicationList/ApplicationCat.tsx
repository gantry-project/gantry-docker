import React, {useCallback, useState} from "react";
import styled from "@emotion/styled";
import {useQuery} from "@tanstack/react-query";
import {useNavigate} from "react-router-dom";
import config from "config/config";
import axios from "axios";


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

const ApplicationCat = () => {
  const [applications, setApplications] = useState<Application[]>([] as Application[]);
  const navigate = useNavigate();

  const convertToApplication = useCallback((dto: ApplicationDto): Application => {
    return {
      id: dto.id.toString(),
      desc: dto.image,
      title: dto.title,
      img: "",
      logo: "",
    }
  }, []);

  const onClickHandler = useCallback((applicationId: string) => {
    navigate(`/applicationDetail/${applicationId}`);
  }, []);

  const MessageDiv = (props: { message: String, condition?: () => boolean }) => {
    if (props.condition && !props.condition()) return null;

    return <MessageBox>
      <MessageItem>
        {props.message}
      </MessageItem>
    </MessageBox>
  }

  const ApplicationDivs = (props: {applications: Application[]}) => {
    if (props.applications.length == 0) return null;
    return <>
      {props.applications.map(app => (
        <ItemWrapper key={app.id} onClick={() => onClickHandler(app.id)}>
          <ImageWrapper>{app.img}</ImageWrapper>
          <ItemBottom>
            <ItemLogoWrapper>
              <Logo>LO</Logo>
            </ItemLogoWrapper>
            <ItemBottomRight>
              <ItemTitle>{app.title}</ItemTitle>
              <ItemDesc>{app.desc}</ItemDesc>
            </ItemBottomRight>
          </ItemBottom>
        </ItemWrapper>
      ))}
    </>
  }

  const {isLoading, isFetching, error} = useQuery<ApplicationDto[], Error, ApplicationDto[]>(
    ["getApplications"],
    () =>
      axios.get(`${config.gantryApiUrl}/applications`)
        .then((res) => res.data),
    {
      onSuccess: (data) => setApplications(data.map(dto => convertToApplication(dto)))
    }
  );

  if (isLoading) {
    return <MessageDiv message={"Loading..."}/>;
  }
  else if (isFetching) {
    return <MessageDiv message={"Updating..."} />;
  }
  else if (error) {
    return <MessageDiv message={"An error has occurred: " + error.toString()} />;
  }

  return (
    <>
      <Category>database</Category>
      <Container>
        <MessageDiv message={"Empty applications"} condition={() => applications.length == 0} />
        <ApplicationDivs applications={applications} />
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
