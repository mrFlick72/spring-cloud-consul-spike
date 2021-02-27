provider "consul" {}

data "consul_keys" "helloservice" {
  key {
    name    = "uppercase"
    path    = "config/helloservice/data"
    default = "false"
  }
}

resource "consul_keys" "helloservice" {

  key {
    value = "uppercase: true"
    path    = "config/helloservice/data"
  }
}