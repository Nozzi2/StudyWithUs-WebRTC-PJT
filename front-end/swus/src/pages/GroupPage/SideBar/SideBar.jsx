import Box from "@mui/material/Box";
import { Button } from "@mui/material";
import "./SideBar.css";
import { useNavigate } from "react-router-dom";
import { v4 as uuidv4 } from "uuid";
import "../../../App.css";


const drawerWidth = 240;

const openedMixin = (theme) => ({
  width: drawerWidth,
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
});



export default function MiniDrawer(props) {

  const navigate = useNavigate();

  const sideItems = window.location.pathname.slice(7, 12) === "board"
    ? [
      { name: "MY STUDY", path: `mystudy`, backgroundColor: "#1A1E33", color: "white" },
      { name: "STUDY BOARD", path: `board`, backgroundColor: "#DEDCEE", color: "#1A1E33"} 
    ]
    : [
      { name: "MY STUDY", path: `mystudy`, backgroundColor: "#DEDCEE", color: "#1A1E33" },
      { name: "STUDY BOARD", path: `board`, backgroundColor: "#1A1E33", color: "white"}
    ]


  return (
    <Box
      sx={{ display: "flex", backgroundColor: "#1A1E33", height: "100vh" }}
      boxSizing={openedMixin}
    >

      <Box fullWidth sx={{ mt: 11, mx: 4, justifyContent: "center" }}>
        {sideItems.map((item) => {
          return (
            <Button
              key={uuidv4()}
              disableRipple 
              variant="contained"
              fullWidth
              style={{
                backgroundColor: item.backgroundColor,
                color: item.color,
                width: "180px",
                height: "60px",
                fontSize: "20px",
                marginBlock: "20px",
              }}
              onClick={() => {navigate(item.path)}}
          ><span style={{ fontFamily: "Cafe24_e" }}>{item.name}</span></Button>
          )
        })}
      </Box>
    </Box>
  );
}
