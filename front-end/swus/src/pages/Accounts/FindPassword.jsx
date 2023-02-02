import React, { useState } from "react";
// import Avatar from '@mui/material/Avatar';
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
// import Link from "@mui/material/Link";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
// import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from "@mui/material/Typography";
import { createTheme, ThemeProvider } from "@mui/material/styles";

import { indigo } from "@mui/material/colors";

import logo from "./../../logo.png";

import axios from "axios";
import { integerPropType } from "@mui/utils";

// function Copyright(props) {
//   return (
//     <Typography variant="body2" color="text.secondary" align="center" {...props}>
//       {'Copyright © '}
//       <Link color="inherit" href="https://mui.com/">
//         Your Website
//       </Link>{' '}
//       {new Date().getFullYear()}
//       {'.'}
//     </Typography>
//   );
// }

const theme = createTheme();

export default function FindPassword() {
  const [Email, setEmail] = useState("");

  const idSubmit = (event) => {
    event.preventDefault();

    const data = new FormData(event.currentTarget);

    const email = data.get("email");

    // useState 확인
    setEmail(email);

    const emailCheck = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z\-]+/;

    if (email) {
      if (!emailCheck.test(email)) {
        alert("이메일 형식을 지켜주세요.");
      } else {
        console.log(email);
        axios
          .get(`http://localhost:8081/auth/check-email?email=${email}`)
          .then((response) => {
            console.log(response.data.msg);
            if (response.data.msg === "Y") {
              alert("가입된 아이디입니다.");
            } else {
              alert("가입되지 않은 아이디입니다.");
            }
          });
      }
    } else {
      alert("정보를 다시 입력해주세요.");
    }
  };

  const passwordSubmit = (event) => {
    event.preventDefault();

    const data = new FormData(event.currentTarget);

    const payload = {
      email: Email,
      question_id: data.get("question"),
      answer: data.get("answer"),
    };

    // 유효성검사
    if (payload.question_id && payload.answer) {
      console.log(payload);

      axios
        .post("http://localhost:8081/auth/check-pwd", payload)
        .then(() => {
          // console.log(response.data.msg);
          alert("입력하신 메일로 비밀번호를 전송했습니다.");
        })
        .catch((error) => {
          if (error.message === "Request failed with status code 400") {
            alert("질문이나 답이 틀렸습니다.");
          } else {
            alert("이메일 전송을 실패했습니다.");
          }
        });
    } else {
      alert("정보를 다시 입력해주세요.");
    }
  };

  const favorite_questions = [
    {
      value: 1,
      label: "기억에 남는 추억의 장소는?",
    },
    {
      value: 2,
      label: "자신의 인생 좌우명은?",
    },
    {
      value: 3,
      label: "자신의 보물 제1호는?",
    },
    {
      value: 4,
      label: "가장 기억에 남는 선생님 성함은?",
    },
    {
      value: 5,
      label: "내가 좋아하는 캐릭터는?",
    },
    {
      value: 6,
      label: "출신 초등학교 이름은?",
    },
  ];

  return (
    <ThemeProvider theme={theme}>
      <Grid container component="main" sx={{ height: "100vh" }}>
        <CssBaseline />
        <Grid
          item
          xs={false}
          sm={4}
          md={7}
          sx={{
            backgroundImage: { logo },
            backgroundRepeat: "no-repeat",
            backgroundColor: indigo[900],
            backgroundSize: "cover",
            backgroundPosition: "center",
          }}
        />

        <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
          <Box
            sx={{
              my: 10,
              mx: 4,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            {/* <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
              <LockOutlinedIcon />
            </Avatar> */}
            <Typography component="h1" variant="h5">
              아이디/비밀번호 찾기
            </Typography>
            <Box component="form" noValidate onSubmit={idSubmit} sx={{ mt: 1 }}>
              아이디 (이메일)
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                // label="Email Address"
                name="email"
                autoComplete="email"
                autoFocus
                variant="standard"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                아이디 확인
              </Button>
            </Box>
            <Box
              component="form"
              noValidate
              onSubmit={passwordSubmit}
              sx={{ mt: 1 }}
            >
              질문
              <TextField
                margin="normal"
                select
                fullWidth
                id="passwordQuestion"
                label="Choose a question"
                defaultValue=""
                name="question"
              >
                {favorite_questions.map((option) => (
                  <MenuItem key={option.value} value={option.value}>
                    {option.label}
                  </MenuItem>
                ))}
              </TextField>
              답
              <TextField
                margin="normal"
                required
                fullWidth
                id="answer"
                // label="answer"
                name="answer"
                autoComplete="answer"
                autoFocus
                variant="standard"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                비밀번호 찾기
              </Button>
              {/* <Copyright sx={{ mt: 5 }} /> */}
            </Box>
          </Box>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}
