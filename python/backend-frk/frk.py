#########################################################################
#
#  Copyright (2017, ) Institute of Software, Chinese Academy of Sciences
#
#  Author: wuheng@{otcaix.iscas.ac.cn}
#  Date  : 2017/8/31
#
#  Support /abcsys/subpath?key=value
#  See http://doc.oschina.net/nacha?v=35510&t=216744
#  
##########################################################################

from __future__ import print_function
import os
import sys
import commands
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import subprocess
from SocketServer import ThreadingMixIn

# absoluted path for abcsys.py
root_path  = sys.path[0]

# Version 1.6
version16_prefix = '/abcsys/1.6/'

version16_directory = "/v16/modules"

# Version 1.0.3
version1_prefix  = '/abcsys/'

version1_directory = '/modules'

# Default module
default_module = 'error'

class ThreadedHTTPServer(ThreadingMixIn, HTTPServer):
    pass

class MainHandler(BaseHTTPRequestHandler):

    # Url should be start with /abcsys/1.6/
    def is_Version16(self):
        return True if (self.path.startswith(version16_prefix) 
                        and len(self.path.split('?')[0].split('/')) == 4 
                        and len(self.path.split('&')) == 1 ) else False
                        
    # Url should be start with /abcsys/
    def is_Version1(self):
        return True if (self.path.startswith(version1_prefix) 
                        and len(self.path.split('?')[0].split('/')) == 3 
                        and len(self.path.split('&')) == 1) else False

    def is_Params_Valid(self):
        params_list = self.path.split('?')
        return True if (len(params_list) == 2 
                        or len(params_list) == 1) else False
    
    def is_Shell_Valid(self, root, name):
        path = root + '/' + name
        if (os.path.isdir(path)):
            return False
        return os.path.exists(path)
                        
    def get_Url_Params(self):
        params = ''
        try:
            #strs =  self.path.split('?')[1].split('=')
            #params = strs[0] + ' ' + strs[1]
            strs =  self.path.split('?')[1]
            index = strs.index('=')
            param1 = strs[:index]
	    param2 = strs[index+1:]
            params = param1 + ' ' + param2
        except:
            # ignore here
            params = ''
        return params;
    
    def get_Default_Shell(self, version):
        return root_path + version + '/' + default_module
    
    # shell location
    def get_Shell_Name(self, version):
        url = self.path.split('?')[0]
        idx = len(version.split('/'))
        name = url.split('/')[idx]
        root = root_path + version
        if self.is_Shell_Valid(root, name):
            return root + '/' + name
        return self.get_Default_Shell(version)
    
    # get command
    def get_Command_From(self, version):
        if (self.is_Params_Valid()):
            return self.get_Shell_Name(version) + ' ' + self.get_Url_Params() 
        else:
            return self.get_Default_Shell(version)
        
    def get_Default_Command(self):
        if (self.path.startswith(version16_prefix)):
            return root_path + version16_directory + '/' + default_module
        elif (self.path.startswith(version1_prefix)):
            return root_path + version1_directory + '/' + default_module
        else:
            return root_path + version16_directory + '/' + default_module
    
    def get_data(self, cmd):
        #output = subprocess.Popen(
        #cmd, shell = True,
        #stdout = subprocess.PIPE)
        #return output.communicate()[0].decode('utf-8').encode('gb2312')
        #return commands.getstatusoutput(cmd)[1].decode('utf-8').encode('gb2312')

   	data = commands.getstatusoutput(cmd)[1].decode('utf-8').encode('gb2312')
        index = cmd.find(' ')
        name = ""
        if index == -1:
            name = cmd[cmd.rfind('/')+1:]
        else:
            temp = cmd[:index]
            name = temp[temp.rfind('/')+1:]
        ignore = name.startswith('_')
        if ignore == True:
            return data
        else:
            index=data.index('{')
            return data[index:]
 
    # response   
    def do_REPLY(self, data):
        self.send_response(200)
        self.send_header('Content-type', 'text/json')
        self.end_headers()
        self.wfile.write(data)

    def do_GET(self):
        cmd = 'bash '
        if self.is_Version16():
            cmd = self.get_Command_From(version16_directory)
        elif self.is_Version1():
            cmd = self.get_Command_From(version1_directory)
        else:
            cmd = self.get_Default_Command()
       
        self.do_REPLY(self.get_data(cmd))
     
if __name__ == '__main__':
    pid = os.fork()
    if pid != 0:
        os._exit(0)
    else:
        server = ThreadedHTTPServer(('0.0.0.0', 9000), MainHandler)
        server.serve_forever()

