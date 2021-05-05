package authz

#import data.acl
#import input 

default allow = false

allow {
  input.method == "POST"
  input.path = ["api", "v1", "users"]
  input.roles[_].authority == "ROLE_ADMIN"
  #token.payload.role == "ROLE_ADMIN" 
}

# Ensure that the token was issued by the user
#user_owns_token { 
#  input.user == token.payload.username
#}

# Helper to get the token payload.
token = {"payload": payload} {
  [header, payload, signature] := io.jwt.decode(input.token)
}
