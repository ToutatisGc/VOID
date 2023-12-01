package cn.toutatis.xvoid.common.standard

/**
 * HttpHeaders
 * @author Toutatis_Gc
 * @since 0.0.0-ALPHA
 */
object HttpHeaders {
    /*
      Void自定义头
     */
    /**
     * 2021-05-10 14:45:41
     * 店铺Id注入
     */
    const val VOID_MCH = "Void-mchId"

    /**
     * 2021-05-10 14:45:41
     * token header
     */
    const val VOID_AUTHENTICATION = "Void-Authentication"

    /**
     * 2021-07-03 23:25:00
     * 远程服务调用注入的请求头
     *
     */
    const val VOID_REMOTE_CALL = "Void-Remote-Call"
    /*
      下列是官方常见Header
     */
    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept
     */
    const val ACCEPT = "Accept"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-CH
     */
    const val ACCEPT_CH = "Accept-CH"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Charset
     */
    const val ACCEPT_CHARSET = "Accept-Charset"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Datetime
     */
    const val ACCEPT_DATETIME = "Accept-Datetime"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Encoding
     */
    const val ACCEPT_ENCODING = "Accept-Encoding"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Ext
     */
    const val ACCEPT_EXT = "Accept-Ext"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Features
     */
    const val ACCEPT_FEATURES = "Accept-Features"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Language
     */
    const val ACCEPT_LANGUAGE = "Accept-Language"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Params
     */
    const val ACCEPT_PARAMS = "Accept-Params"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Accept-Ranges
     */
    const val ACCEPT_RANGES = "Accept-Ranges"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Allow-Credentials
     */
    const val ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Allow-Headers
     */
    const val ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Allow-Methods
     */
    const val ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Allow-Origin
     */
    const val ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Expose-Headers
     */
    const val ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Max-Age
     */
    const val ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Request-Headers
     */
    const val ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Access-Control-Request-Method
     */
    const val ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Age
     */
    const val AGE = "Age"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Allow
     */
    const val ALLOW = "Allow"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Alternates
     */
    const val ALTERNATES = "Alternates"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Authentication-Info
     */
    const val AUTHENTICATION_INFO = "Authentication-Info"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Authorization
     */
    const val AUTHORIZATION = "Authorization"

    /**
     * 2021-05-10 14:39:41 [Simple Header] C-Ext
     */
    const val C_EXT = "C-Ext"

    /**
     * 2021-05-10 14:39:41 [Simple Header] C-Man
     */
    const val C_MAN = "C-Man"

    /**
     * 2021-05-10 14:39:41 [Simple Header] C-Opt
     */
    const val C_OPT = "C-Opt"

    /**
     * 2021-05-10 14:39:41 [Simple Header] C-PEP
     */
    const val C_PEP = "C-PEP"

    /**
     * 2021-05-10 14:39:41 [Simple Header] C-PEP-Info
     */
    const val C_PEP_INFO = "C-PEP-Info"

    /**
     * 2021-05-10 14:39:41 [Simple Header] CONNECT
     */
    const val CONNECT = "CONNECT"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Cache-Control
     */
    const val CACHE_CONTROL = "Cache-Control"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Compliance
     */
    const val COMPLIANCE = "Compliance"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Connection
     */
    const val CONNECTION = "Connection"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Base
     */
    const val CONTENT_BASE = "Content-Base"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Disposition
     */
    const val CONTENT_DISPOSITION = "Content-Disposition"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Encoding
     */
    const val CONTENT_ENCODING = "Content-Encoding"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-ID
     */
    const val CONTENT_ID = "Content-ID"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Language
     */
    const val CONTENT_LANGUAGE = "Content-Language"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Length
     */
    const val CONTENT_LENGTH = "Content-Length"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Location
     */
    const val CONTENT_LOCATION = "Content-Location"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-MD5
     */
    const val CONTENT_MD5 = "Content-MD5"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Range
     */
    const val CONTENT_RANGE = "Content-Range"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Script-Type
     */
    const val CONTENT_SCRIPT_TYPE = "Content-Script-Type"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Security-Policy
     */
    const val CONTENT_SECURITY_POLICY = "Content-Security-Policy"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Style-Type
     */
    const val CONTENT_STYLE_TYPE = "Content-Style-Type"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Transfer-Encoding
     */
    const val CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Type
     */
    const val CONTENT_TYPE = "Content-Type"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Content-Version
     */
    const val CONTENT_VERSION = "Content-Version"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Cookie
     */
    const val COOKIE = "Cookie"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Cost
     */
    const val COST = "Cost"

    /**
     * 2021-05-10 14:39:41 [Simple Header] DAV
     */
    const val DAV = "DAV"

    /**
     * 2021-05-10 14:39:41 [Simple Header] DELETE
     */
    const val DELETE = "DELETE"

    /**
     * 2021-05-10 14:39:41 [Simple Header] DNT
     */
    const val DNT = "DNT"

    /**
     * 2021-05-10 14:39:41 [Simple Header] DPR
     */
    const val DPR = "DPR"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Date
     */
    const val DATE = "Date"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Default-Style
     */
    const val DEFAULT_STYLE = "Default-Style"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Delta-Base
     */
    const val DELTA_BASE = "Delta-Base"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Depth
     */
    const val DEPTH = "Depth"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Derived-From
     */
    const val DERIVED_FROM = "Derived-From"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Destination
     */
    const val DESTINATION = "Destination"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Differential-ID
     */
    const val DIFFERENTIAL_ID = "Differential-ID"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Digest
     */
    const val DIGEST = "Digest"

    /**
     * 2021-05-10 14:39:41 [Simple Header] ETag
     */
    const val ETAG = "ETag"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Expect
     */
    const val EXPECT = "Expect"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Expires
     */
    const val EXPIRES = "Expires"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Ext
     */
    const val EXT = "Ext"

    /**
     * 2021-05-10 14:39:41 [Simple Header] From
     */
    const val FROM = "From"

    /**
     * 2021-05-10 14:39:41 [Simple Header] GET
     */
    const val GET = "GET"

    /**
     * 2021-05-10 14:39:41 [Simple Header] GetProfile
     */
    const val GETPROFILE = "GetProfile"

    /**
     * 2021-05-10 14:39:41 [Simple Header] HEAD
     */
    const val HEAD = "HEAD"

    /**
     * 2021-05-10 14:39:41 [Simple Header] HTTP-date
     */
    const val HTTP_DATE = "HTTP-date"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Host
     */
    const val HOST = "Host"

    /**
     * 2021-05-10 14:39:41 [Simple Header] IM
     */
    const val IM = "IM"

    /**
     * 2021-05-10 14:39:41 [Simple Header] If
     */
    const val IF = "If"

    /**
     * 2021-05-10 14:39:41 [Simple Header] If-Match
     */
    const val IF_MATCH = "If-Match"

    /**
     * 2021-05-10 14:39:41 [Simple Header] If-Modified-Since
     */
    const val IF_MODIFIED_SINCE = "If-Modified-Since"

    /**
     * 2021-05-10 14:39:41 [Simple Header] If-None-Match
     */
    const val IF_NONE_MATCH = "If-None-Match"

    /**
     * 2021-05-10 14:39:41 [Simple Header] If-Range
     */
    const val IF_RANGE = "If-Range"

    /**
     * 2021-05-10 14:39:41 [Simple Header] If-Unmodified-Since
     */
    const val IF_UNMODIFIED_SINCE = "If-Unmodified-Since"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Keep-Alive
     */
    const val KEEP_ALIVE = "Keep-Alive"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Label
     */
    const val LABEL = "Label"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Last-Event-ID
     */
    const val LAST_EVENT_ID = "Last-Event-ID"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Last-Modified
     */
    const val LAST_MODIFIED = "Last-Modified"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Link
     */
    const val LINK = "Link"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Location
     */
    const val LOCATION = "Location"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Lock-Token
     */
    const val LOCK_TOKEN = "Lock-Token"

    /**
     * 2021-05-10 14:39:41 [Simple Header] MIME-Version
     */
    const val MIME_VERSION = "MIME-Version"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Man
     */
    const val MAN = "Man"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Max-Forwards
     */
    const val MAX_FORWARDS = "Max-Forwards"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Media-Range
     */
    const val MEDIA_RANGE = "Media-Range"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Message-ID
     */
    const val MESSAGE_ID = "Message-ID"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Meter
     */
    const val METER = "Meter"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Negotiate
     */
    const val NEGOTIATE = "Negotiate"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Non-Compliance
     */
    const val NON_COMPLIANCE = "Non-Compliance"

    /**
     * 2021-05-10 14:39:41 [Simple Header] OPTION
     */
    const val OPTION = "OPTION"

    /**
     * 2021-05-10 14:39:41 [Simple Header] OPTIONS
     */
    const val OPTIONS = "OPTIONS"

    /**
     * 2021-05-10 14:39:41 [Simple Header] OWS
     */
    const val OWS = "OWS"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Opt
     */
    const val OPT = "Opt"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Optional
     */
    const val OPTIONAL = "Optional"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Ordering-Type
     */
    const val ORDERING_TYPE = "Ordering-Type"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Origin
     */
    const val ORIGIN = "Origin"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Overwrite
     */
    const val OVERWRITE = "Overwrite"

    /**
     * 2021-05-10 14:39:41 [Simple Header] P3P
     */
    const val P3P = "P3P"

    /**
     * 2021-05-10 14:39:41 [Simple Header] PEP
     */
    const val PEP = "PEP"

    /**
     * 2021-05-10 14:39:41 [Simple Header] PICS-Label
     */
    const val PICS_LABEL = "PICS-Label"

    /**
     * 2021-05-10 14:39:41 [Simple Header] POST
     */
    const val POST = "POST"

    /**
     * 2021-05-10 14:39:41 [Simple Header] PUT
     */
    const val PUT = "PUT"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Pep-Info
     */
    const val PEP_INFO = "Pep-Info"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Permanent
     */
    const val PERMANENT = "Permanent"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Position
     */
    const val POSITION = "Position"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Pragma
     */
    const val PRAGMA = "Pragma"

    /**
     * 2021-05-10 14:39:41 [Simple Header] ProfileObject
     */
    const val PROFILE_OBJECT = "ProfileObject"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Protocol
     */
    const val PROTOCOL = "Protocol"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Protocol-Query
     */
    const val PROTOCOL_QUERY = "Protocol-Query"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Protocol-Request
     */
    const val PROTOCOL_REQUEST = "Protocol-Request"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Authenticate
     */
    const val PROXY_AUTHENTICATE = "Proxy-Authenticate"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Authentication-Info
     */
    const val PROXY_AUTHENTICATION_INFO = "Proxy-Authentication-Info"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Authorization
     */
    const val PROXY_AUTHORIZATION = "Proxy-Authorization"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Features
     */
    const val PROXY_FEATURES = "Proxy-Features"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Proxy-Instruction
     */
    const val PROXY_INSTRUCTION = "Proxy-Instruction"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Public
     */
    const val PUBLIC = "Public"

    /**
     * 2021-05-10 14:39:41 [Simple Header] RWS
     */
    const val RWS = "RWS"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Range
     */
    const val RANGE = "Range"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Referer
     */
    const val REFERER = "Referer"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Refresh
     */
    const val REFRESH = "Refresh"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Resolution-Hint
     */
    const val RESOLUTION_HINT = "Resolution-Hint"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Resolver-Location
     */
    const val RESOLVER_LOCATION = "Resolver-Location"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Retry-After
     */
    const val RETRY_AFTER = "Retry-After"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Safe
     */
    const val SAFE = "Safe"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Extensions
     */
    const val SEC_WEBSOCKET_EXTENSIONS = "Sec-Websocket-Extensions"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Key
     */
    const val SEC_WEBSOCKET_KEY = "Sec-Websocket-Key"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Origin
     */
    const val SEC_WEBSOCKET_ORIGIN = "Sec-Websocket-Origin"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Protocol
     */
    const val SEC_WEBSOCKET_PROTOCOL = "Sec-Websocket-Protocol"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Sec-Websocket-Version
     */
    const val SEC_WEBSOCKET_VERSION = "Sec-Websocket-Version"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Security-Scheme
     */
    const val SECURITY_SCHEME = "Security-Scheme"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Server
     */
    const val SERVER = "Server"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Set-Cookie
     */
    const val SET_COOKIE = "Set-Cookie"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Set-Cookie2
     */
    const val SET_COOKIE2 = "Set-Cookie2"

    /**
     * 2021-05-10 14:39:41 [Simple Header] SetProfile
     */
    const val SETPROFILE = "SetProfile"

    /**
     * 2021-05-10 14:39:41 [Simple Header] SoapAction
     */
    const val SOAPACTION = "SoapAction"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Status
     */
    const val STATUS = "Status"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Status-URI
     */
    const val STATUS_URI = "Status-URI"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Strict-Transport-Security
     */
    const val STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security"

    /**
     * 2021-05-10 14:39:41 [Simple Header] SubOK
     */
    const val SUBOK = "SubOK"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Subst
     */
    const val SUBST = "Subst"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Surrogate-Capability
     */
    const val SURROGATE_CAPABILITY = "Surrogate-Capability"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Surrogate-Control
     */
    const val SURROGATE_CONTROL = "Surrogate-Control"

    /**
     * 2021-05-10 14:39:41 [Simple Header] TCN
     */
    const val TCN = "TCN"

    /**
     * 2021-05-10 14:39:41 [Simple Header] TE
     */
    const val TE = "TE"

    /**
     * 2021-05-10 14:39:41 [Simple Header] TRACE
     */
    const val TRACE = "TRACE"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Timeout
     */
    const val TIMEOUT = "Timeout"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Title
     */
    const val TITLE = "Title"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Trailer
     */
    const val TRAILER = "Trailer"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Transfer-Encoding
     */
    const val TRANSFER_ENCODING = "Transfer-Encoding"

    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Color
     */
    const val UA_COLOR = "UA-Color"

    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Media
     */
    const val UA_MEDIA = "UA-Media"

    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Pixels
     */
    const val UA_PIXELS = "UA-Pixels"

    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Resolution
     */
    const val UA_RESOLUTION = "UA-Resolution"

    /**
     * 2021-05-10 14:39:41 [Simple Header] UA-Windowpixels
     */
    const val UA_WINDOWPIXELS = "UA-Windowpixels"

    /**
     * 2021-05-10 14:39:41 [Simple Header] URI
     */
    const val URI = "URI"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Upgrade
     */
    const val UPGRADE = "Upgrade"

    /**
     * 2021-05-10 14:39:41 [Simple Header] User-Agent
     */
    const val USER_AGENT = "User-Agent"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Variant-Vary
     */
    const val VARIANT_VARY = "Variant-Vary"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Vary
     */
    const val VARY = "Vary"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Version
     */
    const val VERSION = "Version"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Via
     */
    const val VIA = "Via"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Viewport-Width
     */
    const val VIEWPORT_WIDTH = "Viewport-Width"

    /**
     * 2021-05-10 14:39:41 [Simple Header] WWW-Authenticate
     */
    const val WWW_AUTHENTICATE = "WWW-Authenticate"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Want-Digest
     */
    const val WANT_DIGEST = "Want-Digest"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Warning
     */
    const val WARNING = "Warning"

    /**
     * 2021-05-10 14:39:41 [Simple Header] Width
     */
    const val WIDTH = "Width"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Content-Duration
     */
    const val X_CONTENT_DURATION = "X-Content-Duration"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Content-Security-Policy
     */
    const val X_CONTENT_SECURITY_POLICY = "X-Content-Security-Policy"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Content-Type-Options
     */
    const val X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-CustomHeader
     */
    const val X_CUSTOMHEADER = "X-CustomHeader"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-DNSPrefetch-Control
     */
    const val X_DNSPREFETCH_CONTROL = "X-DNSPrefetch-Control"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Forwarded-For
     */
    const val X_FORWARDED_FOR = "X-Forwarded-For"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Forwarded-Port
     */
    const val X_FORWARDED_PORT = "X-Forwarded-Port"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Forwarded-Proto
     */
    const val X_FORWARDED_PROTO = "X-Forwarded-Proto"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Frame-Options
     */
    const val X_FRAME_OPTIONS = "X-Frame-Options"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Modified
     */
    const val X_MODIFIED = "X-Modified"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-OTHER
     */
    const val X_OTHER = "X-OTHER"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-PING
     */
    const val X_PING = "X-PING"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-PINGOTHER
     */
    const val X_PINGOTHER = "X-PINGOTHER"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Powered-By
     */
    const val X_POWERED_BY = "X-Powered-By"

    /**
     * 2021-05-10 14:39:41 [Simple Header] X-Requested-With
     */
    const val X_REQUESTED_WITH = "X-Requested-With"
}