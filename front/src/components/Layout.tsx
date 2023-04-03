<<<<<<< HEAD
import React, { FC, ReactNode } from "react";
import styled from "@emotion/styled";

//components
import Navbar from "./Navbar";
import Footer from "./Footer";

interface LayoutProps {
  children: ReactNode;
}

const Layout: FC<LayoutProps> = ({ children }) => {
  return (
    <Container>
      <Navbar />
      {children}
      <Footer />
    </Container>
  );
};

export default Layout;

const Container = styled.div``;
=======
import React, { FC, ReactNode } from "react";
import styled from "@emotion/styled";

//components
import Navbar from "./Navbar";
import Footer from "./Footer";

interface LayoutProps {
  children: ReactNode;
}

const Layout: FC<LayoutProps> = ({ children }) => {
  return (
    <Container>
      <Navbar />
      {children}
      <Footer />
    </Container>
  );
};

export default Layout;

const Container = styled.div``;
>>>>>>> 2916dd479f69ed343ba47b234452038c6e8999f3
