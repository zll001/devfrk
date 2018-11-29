# Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
# Author wuheng@iscas.ac.cn
# Date   2018-11-29

Name: _NAME_
Version: _VERSION_
Release: qcase._GIT_%{?dist}
Source: %{name}-%{version}.tar.gz
Packager: _MAINTAINER_
License: ASL 2.0
Requires: _DEP_ 

%description
_NAME_-_VERSION_

%prep
%setup -n %{name}

%install
mkdir -p %{buildroot}_WORKDIR_
cp -r /root/rpmbuild/BUILD/%{name}* %{buildroot}_WORKDIR_

%files
_WORKDIR_

