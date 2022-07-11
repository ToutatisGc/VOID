package cn.toutatis.xvoid.spring.core.security;

/**
 * 解析所有请求头
 * @author Toutatis_Gc
 * @since 0.0.0-ALPHA
 */
public class VoidHeader {

    /*
      Void自定义头
     */

    /**
     *  2021-05-10 14:45:41
     * 店铺Id注入
     */
    public static final String VOID_MCH = "Void-mchId";

    /**
     *  2021-05-10 14:45:41
     * token header
     */
    public static final String VOID_TOKEN = "Void-Token";

    /**
     *  2021-07-03 23:25:00
     *  远程服务调用注入的请求头
     *
     */
    public static final String VOID_REMOTE_CALL = "Void-Remote-Call";

    /*
      下列是官方常见Header
     */

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept
     */
    public static final String ACCEPT = "Accept";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-CH
     */
    public static final String ACCEPT_CH = "Accept-CH";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Charset
     */
    public static final String ACCEPT_CHARSET = "Accept-Charset";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Datetime
     */
    public static final String ACCEPT_DATETIME = "Accept-Datetime";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Encoding
     */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Ext
     */
    public static final String ACCEPT_EXT = "Accept-Ext";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Features
     */
    public static final String ACCEPT_FEATURES = "Accept-Features";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Language
     */
    public static final String ACCEPT_LANGUAGE = "Accept-Language";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Params
     */
    public static final String ACCEPT_PARAMS = "Accept-Params";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Ranges
     */
    public static final String ACCEPT_RANGES = "Accept-Ranges";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Allow-Credentials
     */
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Allow-Headers
     */
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Allow-Methods
     */
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Allow-Origin
     */
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Expose-Headers
     */
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Max-Age
     */
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Request-Headers
     */
    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Request-Method
     */
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Age
     */
    public static final String AGE = "Age";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Allow
     */
    public static final String ALLOW = "Allow";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Alternates
     */
    public static final String ALTERNATES = "Alternates";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Authentication-Info
     */
    public static final String AUTHENTICATION_INFO = "Authentication-Info";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Authorization
     */
    public static final String AUTHORIZATION = "Authorization";


    /**
     * 2021-05-10 14:39:41 [Simple Header] C-Ext
     */
    public static final String C_EXT = "C-Ext";


    /**
     * 2021-05-10 14:39:41 [Simple Header] C-Man
     */
    public static final String C_MAN = "C-Man";


    /**
     * 2021-05-10 14:39:41 [Simple Header] C-Opt
     */
    public static final String C_OPT = "C-Opt";


    /**
     * 2021-05-10 14:39:41 [Simple Header] C-PEP
     */
    public static final String C_PEP = "C-PEP";


    /**
     * 2021-05-10 14:39:41 [Simple Header] C-PEP-Info
     */
    public static final String C_PEP_INFO = "C-PEP-Info";


    /**
     * 2021-05-10 14:39:41 [Simple Header] CONNECT
     */
    public static final String CONNECT = "CONNECT";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Cache-Control
     */
    public static final String CACHE_CONTROL = "Cache-Control";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Compliance
     */
    public static final String COMPLIANCE = "Compliance";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Connection
     */
    public static final String CONNECTION = "Connection";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Base
     */
    public static final String CONTENT_BASE = "Content-Base";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Disposition
     */
    public static final String CONTENT_DISPOSITION = "Content-Disposition";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Encoding
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-ID
     */
    public static final String CONTENT_ID = "Content-ID";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Language
     */
    public static final String CONTENT_LANGUAGE = "Content-Language";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Length
     */
    public static final String CONTENT_LENGTH = "Content-Length";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Location
     */
    public static final String CONTENT_LOCATION = "Content-Location";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-MD5
     */
    public static final String CONTENT_MD5 = "Content-MD5";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Range
     */
    public static final String CONTENT_RANGE = "Content-Range";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Script-Type
     */
    public static final String CONTENT_SCRIPT_TYPE = "Content-Script-Type";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Security-Policy
     */
    public static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Style-Type
     */
    public static final String CONTENT_STYLE_TYPE = "Content-Style-Type";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Transfer-Encoding
     */
    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Type
     */
    public static final String CONTENT_TYPE = "Content-Type";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Version
     */
    public static final String CONTENT_VERSION = "Content-Version";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Cookie
     */
    public static final String COOKIE = "Cookie";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Cost
     */
    public static final String COST = "Cost";


    /**
     * 2021-05-10 14:39:41 [Simple Header] DAV
     */
    public static final String DAV = "DAV";


    /**
     * 2021-05-10 14:39:41 [Simple Header] DELETE
     */
    public static final String DELETE = "DELETE";


    /**
     * 2021-05-10 14:39:41 [Simple Header] DNT
     */
    public static final String DNT = "DNT";


    /**
     * 2021-05-10 14:39:41 [Simple Header] DPR
     */
    public static final String DPR = "DPR";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Date
     */
    public static final String DATE = "Date";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Default-Style
     */
    public static final String DEFAULT_STYLE = "Default-Style";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Delta-Base
     */
    public static final String DELTA_BASE = "Delta-Base";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Depth
     */
    public static final String DEPTH = "Depth";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Derived-From
     */
    public static final String DERIVED_FROM = "Derived-From";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Destination
     */
    public static final String DESTINATION = "Destination";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Differential-ID
     */
    public static final String DIFFERENTIAL_ID = "Differential-ID";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Digest
     */
    public static final String DIGEST = "Digest";


    /**
     * 2021-05-10 14:39:41 [Simple Header] ETag
     */
    public static final String ETAG = "ETag";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Expect
     */
    public static final String EXPECT = "Expect";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Expires
     */
    public static final String EXPIRES = "Expires";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Ext
     */
    public static final String EXT = "Ext";


    /**
     * 2021-05-10 14:39:41 [Simple Header] From
     */
    public static final String FROM = "From";


    /**
     * 2021-05-10 14:39:41 [Simple Header] GET
     */
    public static final String GET = "GET";


    /**
     * 2021-05-10 14:39:41 [Simple Header] GetProfile
     */
    public static final String GETPROFILE = "GetProfile";


    /**
     * 2021-05-10 14:39:41 [Simple Header] HEAD
     */
    public static final String HEAD = "HEAD";


    /**
     * 2021-05-10 14:39:41 [Simple Header] HTTP-date
     */
    public static final String HTTP_DATE = "HTTP-date";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Host
     */
    public static final String HOST = "Host";


    /**
     * 2021-05-10 14:39:41 [Simple Header] IM
     */
    public static final String IM = "IM";


    /**
     * 2021-05-10 14:39:41 [Simple Header] If
     */
    public static final String IF = "If";


    /**
     * 2021-05-10 14:39:41 [Simple Header] If-Match
     */
    public static final String IF_MATCH = "If-Match";


    /**
     * 2021-05-10 14:39:41 [Simple Header] If-Modified-Since
     */
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";


    /**
     * 2021-05-10 14:39:41 [Simple Header] If-None-Match
     */
    public static final String IF_NONE_MATCH = "If-None-Match";


    /**
     * 2021-05-10 14:39:41 [Simple Header] If-Range
     */
    public static final String IF_RANGE = "If-Range";


    /**
     * 2021-05-10 14:39:41 [Simple Header] If-Unmodified-Since
     */
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Keep-Alive
     */
    public static final String KEEP_ALIVE = "Keep-Alive";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Label
     */
    public static final String LABEL = "Label";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Last-Event-ID
     */
    public static final String LAST_EVENT_ID = "Last-Event-ID";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Last-Modified
     */
    public static final String LAST_MODIFIED = "Last-Modified";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Link
     */
    public static final String LINK = "Link";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Location
     */
    public static final String LOCATION = "Location";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Lock-Token
     */
    public static final String LOCK_TOKEN = "Lock-Token";


    /**
     * 2021-05-10 14:39:41 [Simple Header] MIME-Version
     */
    public static final String MIME_VERSION = "MIME-Version";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Man
     */
    public static final String MAN = "Man";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Max-Forwards
     */
    public static final String MAX_FORWARDS = "Max-Forwards";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Media-Range
     */
    public static final String MEDIA_RANGE = "Media-Range";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Message-ID
     */
    public static final String MESSAGE_ID = "Message-ID";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Meter
     */
    public static final String METER = "Meter";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Negotiate
     */
    public static final String NEGOTIATE = "Negotiate";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Non-Compliance
     */
    public static final String NON_COMPLIANCE = "Non-Compliance";


    /**
     * 2021-05-10 14:39:41 [Simple Header] OPTION
     */
    public static final String OPTION = "OPTION";


    /**
     * 2021-05-10 14:39:41 [Simple Header] OPTIONS
     */
    public static final String OPTIONS = "OPTIONS";


    /**
     * 2021-05-10 14:39:41 [Simple Header] OWS
     */
    public static final String OWS = "OWS";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Opt
     */
    public static final String OPT = "Opt";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Optional
     */
    public static final String OPTIONAL = "Optional";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Ordering-Type
     */
    public static final String ORDERING_TYPE = "Ordering-Type";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Origin
     */
    public static final String ORIGIN = "Origin";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Overwrite
     */
    public static final String OVERWRITE = "Overwrite";


    /**
     * 2021-05-10 14:39:41 [Simple Header] P3P
     */
    public static final String P3P = "P3P";


    /**
     * 2021-05-10 14:39:41 [Simple Header] PEP
     */
    public static final String PEP = "PEP";


    /**
     * 2021-05-10 14:39:41 [Simple Header] PICS-Label
     */
    public static final String PICS_LABEL = "PICS-Label";


    /**
     * 2021-05-10 14:39:41 [Simple Header] POST
     */
    public static final String POST = "POST";


    /**
     * 2021-05-10 14:39:41 [Simple Header] PUT
     */
    public static final String PUT = "PUT";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Pep-Info
     */
    public static final String PEP_INFO = "Pep-Info";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Permanent
     */
    public static final String PERMANENT = "Permanent";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Position
     */
    public static final String POSITION = "Position";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Pragma
     */
    public static final String PRAGMA = "Pragma";


    /**
     * 2021-05-10 14:39:41 [Simple Header] ProfileObject
     */
    public static final String PROFILEOBJECT = "ProfileObject";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Protocol
     */
    public static final String PROTOCOL = "Protocol";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Protocol-Query
     */
    public static final String PROTOCOL_QUERY = "Protocol-Query";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Protocol-Request
     */
    public static final String PROTOCOL_REQUEST = "Protocol-Request";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Authenticate
     */
    public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Authentication-Info
     */
    public static final String PROXY_AUTHENTICATION_INFO = "Proxy-Authentication-Info";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Authorization
     */
    public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Features
     */
    public static final String PROXY_FEATURES = "Proxy-Features";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Instruction
     */
    public static final String PROXY_INSTRUCTION = "Proxy-Instruction";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Public
     */
    public static final String PUBLIC = "Public";


    /**
     * 2021-05-10 14:39:41 [Simple Header] RWS
     */
    public static final String RWS = "RWS";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Range
     */
    public static final String RANGE = "Range";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Referer
     */
    public static final String REFERER = "Referer";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Refresh
     */
    public static final String REFRESH = "Refresh";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Resolution-Hint
     */
    public static final String RESOLUTION_HINT = "Resolution-Hint";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Resolver-Location
     */
    public static final String RESOLVER_LOCATION = "Resolver-Location";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Retry-After
     */
    public static final String RETRY_AFTER = "Retry-After";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Safe
     */
    public static final String SAFE = "Safe";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Extensions
     */
    public static final String SEC_WEBSOCKET_EXTENSIONS = "Sec-Websocket-Extensions";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Key
     */
    public static final String SEC_WEBSOCKET_KEY = "Sec-Websocket-Key";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Origin
     */
    public static final String SEC_WEBSOCKET_ORIGIN = "Sec-Websocket-Origin";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Protocol
     */
    public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-Websocket-Protocol";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Version
     */
    public static final String SEC_WEBSOCKET_VERSION = "Sec-Websocket-Version";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Security-Scheme
     */
    public static final String SECURITY_SCHEME = "Security-Scheme";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Server
     */
    public static final String SERVER = "Server";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Set-Cookie
     */
    public static final String SET_COOKIE = "Set-Cookie";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Set-Cookie2
     */
    public static final String SET_COOKIE2 = "Set-Cookie2";


    /**
     * 2021-05-10 14:39:41 [Simple Header] SetProfile
     */
    public static final String SETPROFILE = "SetProfile";


    /**
     * 2021-05-10 14:39:41 [Simple Header] SoapAction
     */
    public static final String SOAPACTION = "SoapAction";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Status
     */
    public static final String STATUS = "Status";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Status-URI
     */
    public static final String STATUS_URI = "Status-URI";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Strict-Transport-Security
     */
    public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";


    /**
     * 2021-05-10 14:39:41 [Simple Header] SubOK
     */
    public static final String SUBOK = "SubOK";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Subst
     */
    public static final String SUBST = "Subst";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Surrogate-Capability
     */
    public static final String SURROGATE_CAPABILITY = "Surrogate-Capability";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Surrogate-Control
     */
    public static final String SURROGATE_CONTROL = "Surrogate-Control";


    /**
     * 2021-05-10 14:39:41 [Simple Header] TCN
     */
    public static final String TCN = "TCN";


    /**
     * 2021-05-10 14:39:41 [Simple Header] TE
     */
    public static final String TE = "TE";


    /**
     * 2021-05-10 14:39:41 [Simple Header] TRACE
     */
    public static final String TRACE = "TRACE";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Timeout
     */
    public static final String TIMEOUT = "Timeout";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Title
     */
    public static final String TITLE = "Title";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Trailer
     */
    public static final String TRAILER = "Trailer";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Transfer-Encoding
     */
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";


    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Color
     */
    public static final String UA_COLOR = "UA-Color";


    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Media
     */
    public static final String UA_MEDIA = "UA-Media";


    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Pixels
     */
    public static final String UA_PIXELS = "UA-Pixels";


    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Resolution
     */
    public static final String UA_RESOLUTION = "UA-Resolution";


    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Windowpixels
     */
    public static final String UA_WINDOWPIXELS = "UA-Windowpixels";


    /**
     * 2021-05-10 14:39:41 [Simple Header] URI
     */
    public static final String URI = "URI";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Upgrade
     */
    public static final String UPGRADE = "Upgrade";


    /**
     * 2021-05-10 14:39:41 [Simple Header] User-Agent
     */
    public static final String USER_AGENT = "User-Agent";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Variant-Vary
     */
    public static final String VARIANT_VARY = "Variant-Vary";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Vary
     */
    public static final String VARY = "Vary";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Version
     */
    public static final String VERSION = "Version";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Via
     */
    public static final String VIA = "Via";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Viewport-Width
     */
    public static final String VIEWPORT_WIDTH = "Viewport-Width";


    /**
     * 2021-05-10 14:39:41 [Simple Header] WWW-Authenticate
     */
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Want-Digest
     */
    public static final String WANT_DIGEST = "Want-Digest";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Warning
     */
    public static final String WARNING = "Warning";


    /**
     * 2021-05-10 14:39:41 [Simple Header] Width
     */
    public static final String WIDTH = "Width";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Content-Duration
     */
    public static final String X_CONTENT_DURATION = "X-Content-Duration";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Content-Security-Policy
     */
    public static final String X_CONTENT_SECURITY_POLICY = "X-Content-Security-Policy";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Content-Type-Options
     */
    public static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-CustomHeader
     */
    public static final String X_CUSTOMHEADER = "X-CustomHeader";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-DNSPrefetch-Control
     */
    public static final String X_DNSPREFETCH_CONTROL = "X-DNSPrefetch-Control";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Forwarded-For
     */
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Forwarded-Port
     */
    public static final String X_FORWARDED_PORT = "X-Forwarded-Port";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Forwarded-Proto
     */
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Frame-Options
     */
    public static final String X_FRAME_OPTIONS = "X-Frame-Options";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Modified
     */
    public static final String X_MODIFIED = "X-Modified";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-OTHER
     */
    public static final String X_OTHER = "X-OTHER";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-PING
     */
    public static final String X_PING = "X-PING";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-PINGOTHER
     */
    public static final String X_PINGOTHER = "X-PINGOTHER";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Powered-By
     */
    public static final String X_POWERED_BY = "X-Powered-By";


    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Requested-With
     */
    public static final String X_REQUESTED_WITH = "X-Requested-With";



    public static void main(String[] args) {
        //使用逗号分割
        String allHeader = "";
        printHeader(allHeader);
    }

    private static void printHeader(String allHeader){
//        if (!"".equals(allHeader)){
//            String[] headers = allHeader.split(",");
//            for (String header : headers) {
//                String trim = header.trim();
//                String currentTime = TimeConstant.INSTANCE.getCurrentTime();
//                System.err.println("\r\n/**");
//                System.err.println(" * "+ currentTime +" [Simple Header] "+trim);
//                System.err.println("*/");
//                System.err.println("public static final String "+ trim.toUpperCase().replace("-","_") +" = \""+ trim +"\";\r\n");
//            }
//        }
    }

}
